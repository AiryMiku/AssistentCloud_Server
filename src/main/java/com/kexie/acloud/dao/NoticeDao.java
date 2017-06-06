package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Notice;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.NoticeException;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.RedisUtil;

import com.kexie.acloud.util.SendPushMsgRunnable;
import com.kexie.acloud.util.SendRealTImePushMsgRunnable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by zojian on 2017/5/8.
 */
@Repository
@Transactional
public class NoticeDao implements INoticeDao {

    @Autowired
    TaskExecutor taskExecutor;

    @Autowired(required = false)
    StringRedisTemplate redisTemplate;

    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

    @Autowired
    SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean addNotice(Notice notice,String userId) {
        try {
            if(notice.getPublisher()==null) {
                User user = new User();
                user.setUserId(userId);
                notice.setPublisher(user);
            }
            Session session = getCurrentSession();
            //Transaction transaction = session.beginTransaction();
            session.save(notice);
           // transaction.commit();


            taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                    "notice",
                    notice.getId(),
                    notice.getTitle(),
                    notice.getExecutors()));
            // 向所有参与者发送新公告通知
            taskExecutor.execute(new SendPushMsgRunnable(jedisConnectionFactory.getJedis(),
                    "notice",
                    notice.getId(),
                    notice.getTitle(),
                    notice.getExecutors()));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateNotice(int notice_id, Notice notice, String user_id) {

        Notice oldNotice = getCurrentSession().get(Notice.class, notice_id);
        if (!notice.getPublisher().getUserId().equals(oldNotice.getPublisher().getUserId())
                || !notice.getPublisher().getUserId().equals(user_id)) {
            //发布者信息不能修改
            return false;
        }
        try {
            notice.setId(oldNotice.getId());
            notice.setTime(new Date());
            getCurrentSession().evict(oldNotice);
            getCurrentSession().update(notice);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteNotice(int notice_id, String user_id) {
        try {
            Notice notice = getNoticeByNoticeId(notice_id, user_id,null);
            if (notice.getPublisher().getUserId().equals(user_id)) {
                notice.setStatus((short) 1);
                getCurrentSession().update(notice);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Notice> getNoticesByUserId(String user_id, int page, int pageSize) {
        String hql = "FROM Notice WHERE (publisher_id = ? AND notice_status = 0)OR ? in elements(executors) ORDER BY time DESC";
        User user = new User();
        user.setUserId(user_id);
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter(0, user_id);
        query.setParameter(1, user);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Notice> getNoticesByUserIdAndSocietyId(String user_id, int society_id, int page, int pageSize) {
        String hql = "FROM Notice WHERE (publisher_id = ? AND society_id = ? AND notice_status = 0) OR ? in elements(executors) ORDER BY time DESC";
        User user = new User();
        user.setUserId(user_id);
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter(0, user_id);
        query.setParameter(1, society_id);
        query.setParameter(2, user);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Notice> getNoticesByPublisherId(String publisher_id, int page, int pageSize) {
        String hql = "FROM Notice WHERE publisher_id = ? AND notice_status = 0 ORDER BY time DESC";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter(0, publisher_id);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public Notice getNoticeByNoticeId(int noticeId, String userId, String identifier) throws NoticeException {
        if(!getPermission(noticeId,userId))
            throw new NoticeException("没有权限");
        RedisUtil.deleteMsg(jedisConnectionFactory.getJedis(),userId,identifier,"notice");
        String notice_visitor = "notice:visitor:" + noticeId;
        Notice notice = getCurrentSession().get(Notice.class, noticeId);
        if(notice.getVisitor_status()==0){
            // 未被所有人查看
            if(!userId.equals(notice.getPublisher().getUserId())) {
                // 不记录发布者的查看记录
                redisTemplate.boundSetOps(notice_visitor).add(userId);
            }
            if(redisTemplate.boundSetOps(notice_visitor).size()==notice.getExecutors().size()){
                // 公告可见者都查看了公告,将状态持久化到MySQL
                notice.setVisitor_status((short) 1);
                updateNotice(notice.getId(),notice,notice.getPublisher().getUserId());
                // 设置键过期时间（5天）
                redisTemplate.boundSetOps(notice_visitor).expire(5,TimeUnit.DAYS);
            }
        }
        return notice;
    }

    @Override
    public Set<String> getNoticeVisitorByNoticeId(int notice_id, String user_id) throws NoticeException {

        if (!getPermission(notice_id, user_id))
            throw new NoticeException("没有权限");

        Set<String> result = RedisUtil.getNoticeVisitor(jedisConnectionFactory.getJedis(), notice_id);
        if (result.size() == 0) {
            // 缓存未命中,将MySQL中数据载入redis
            Notice notice = getCurrentSession().get(Notice.class, notice_id);

            // 已经被所有人浏览过（即已经将状态持久化到MySQL）
            if (notice.getVisitor_status() == 1) {
                RedisUtil.loadNoticeVisitorByMySQL(jedisConnectionFactory.getJedis(),notice_id, notice.getExecutors());
                for (User user : notice.getExecutors()) {
                    result.add(user.getUserId());
                }
            }
        }
            return result;
    }

    @Override
    public boolean getPermission(int notice_id,String user_id) {
        Notice notice = getCurrentSession().get(Notice.class, notice_id);
        // 是否公告发布者
        if(notice.getPublisher().getUserId().equals(user_id))
            return true;

        // 判断是否该公告的参与者
        List<User> userList = notice.getExecutors();

        for (User user: userList) {
            if(user.getUserId().equals(user_id))
                return true;
        }
        return false;
    }
}
