package com.kexie.acloud.service;

import com.kexie.acloud.dao.INoticeDao;
import com.kexie.acloud.dao.ISocietyDao;
import com.kexie.acloud.dao.IUserDao;
import com.kexie.acloud.domain.Notice;
import com.kexie.acloud.exception.NoticeException;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.SendPushMsgRunnable;
import com.kexie.acloud.util.SendRealTImePushMsgRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Set;

/**
 * Created by zojian on 2017/5/8.
 */
@Service
public class NoticeService implements INoticeService {

    @Autowired
    TaskExecutor taskExecutor;

    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

    @Resource
    ISocietyDao mSocietyDao;

    @Autowired
    IUserDao userDao;

    @Autowired(required = false)
    INoticeDao noticeDao;

    @Override
    public boolean addNotice(Notice notice,String userId) throws AuthenticationException {

        // 加载社团完整数据
        notice.setSociety(mSocietyDao.getSocietyById(notice.getSociety().getId()));

        if(!mSocietyDao.isInSociety(notice.getSociety().getId(),userId)){
            throw new AuthenticationException("用户 " + userId + " 不在当前社团 " + notice.getSociety().getName() + " 中");
        }
        if (!mSocietyDao.isInSociety(notice.getSociety().getId(), notice.getExecutors())) {
            throw new AuthenticationException("有一些执行者不在当前社团 " + notice.getSociety().getName() + " 中");
        }

        if(noticeDao.addNotice(notice,userId)) {

            // 向所有在线的参与者发送新公告通知
            taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                    "notice",
                    notice.getId(),
                    notice.getPublisher().getUserId(),
                    notice.getPublisher().getLogoUrl(),
                    "你有一条新的公告通知，快去查看吧❤️",
                    notice.getTitle(),
                    notice.getExecutors()));
            // 向所有参与者发送新公告通知
            taskExecutor.execute(new SendPushMsgRunnable(jedisConnectionFactory.getJedis(),
                    "notice",
                    notice.getId(),
                    notice.getPublisher().getUserId(),
                    notice.getPublisher().getLogoUrl(),
                    "你有一条新的公告通知，快去查看吧❤️",
                    notice.getTitle(),
                    notice.getExecutors()));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateNotice(int notice_id,Notice newNotice, String user_id) throws NoticeException {
        if(noticeDao.getNoticeByNoticeId(notice_id,user_id,null)==null){
            throw new NoticeException("公告不存在,公告ID有误");
        }
        if(noticeDao.updateNotice(notice_id,newNotice,user_id)){
            // 向所有在线的参与者发送新公告通知
            taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                    "notice",
                    newNotice.getId(),
                    newNotice.getPublisher().getUserId(),
                    newNotice.getPublisher().getLogoUrl(),
                    "你有一条公告更新了，快去查看吧❤️",
                    newNotice.getTitle(),
                    newNotice.getExecutors()));
            // 向所有参与者发送新公告通知
            taskExecutor.execute(new SendPushMsgRunnable(jedisConnectionFactory.getJedis(),
                    "notice",
                    newNotice.getId(),
                    newNotice.getPublisher().getUserId(),
                    newNotice.getPublisher().getLogoUrl(),
                    "你有一条新的公告通知，快去查看吧❤️",
                    newNotice.getTitle(),
                    newNotice.getExecutors()));
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteNotice(int notice_id, String user_id) throws NoticeException {
        if(noticeDao.getNoticeByNoticeId(notice_id,user_id,null)==null){
            throw new NoticeException("公告不存在,公告ID有误");
        }
        return noticeDao.deleteNotice(notice_id, user_id);
    }

    @Override
    public List<Notice> getNoticesByUserId(String user_id, int page, int pageSize) {
        return noticeDao.getNoticesByUserId(user_id,page,pageSize);
    }

    @Override
    public List<Notice> getNoticesByPublisherId(String publisher_id, int page, int pageSize) {
        return noticeDao.getNoticesByPublisherId(publisher_id,page,pageSize);
    }

    @Override
    public List<Notice> getNoticesByUserIdAndSocietyId(String user_id, int society_id, int page,int pageSize) {
        return noticeDao.getNoticesByUserIdAndSocietyId(user_id,society_id,page,pageSize);
    }

    @Override
    public Notice getNoticeByNoticeId(int noticeId,String userId,String identifier) throws NoticeException {
        if(!noticeDao.getPermission(noticeId,userId))
            throw new NoticeException("没有权限");
        return noticeDao.getNoticeByNoticeId(noticeId,userId,identifier);
    }

    @Override
    public Set<String> getNoticeVisitorByNoticeId(int noticeId, String userId) throws NoticeException {
        if(!noticeDao.getPermission(noticeId,userId))
            throw new NoticeException("没有权限");
        return noticeDao.getNoticeVisitorByNoticeId(noticeId,userId);
    }
}
