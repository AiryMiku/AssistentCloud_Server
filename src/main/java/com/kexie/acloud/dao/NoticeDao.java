package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Notice;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.util.BeanUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zojian on 2017/5/8.
 */
@Repository
@Transactional
public class NoticeDao implements INoticeDao {
//    @Autowired
//    public void setSuperSessionFactory(SessionFactory sessionFactory) {
//        super.setSessionFactory(sessionFactory);
//    }
    @Autowired
    SessionFactory sessionFactory;
    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean addNotice(Notice notice) {
        try {
            getCurrentSession().save(notice);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateNotice(Notice notice) {
        try {
            Notice newNotice = getCurrentSession().get(Notice.class,notice.getId());
            BeanUtil.copyProperties(notice,newNotice);
            getCurrentSession().save(newNotice);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteNotice(Notice notice) {
        return false;
    }

    @Override
    public List<Notice> getNoticesByUserId(String user_id, int page,int pageSize) {
        String hql = "FROM Notice WHERE publisher_id = ? OR ? in elements(executors) ORDER BY time DESC";
        User user = new User();
        user.setUserId(user_id);
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter(0,user_id);
        query.setParameter(1,user);
        query.setFirstResult((page-1)*pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<Notice> getNoticesByUserIdAndSocietyId(String user_id, int society_id, int page,int pageSize) {
        String hql = "FROM Notice WHERE (publisher_id = ? AND society_id = ?) OR ? in elements(executors) ORDER BY time DESC";
        User user = new User();
        user.setUserId(user_id);
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter(0,user_id);
        query.setParameter(1,society_id);
        query.setParameter(2,user);
        query.setFirstResult((page-1)*pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<Notice> getNoticesByPublisherId(String publisher_id, int page,int pageSize) {
        String hql = "FROM Notice WHERE publisher_id = ? ORDER BY time DESC";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter(0,publisher_id);
        query.setFirstResult((page-1)*pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public Notice getNoticeByNoticeId(int notice_id) {
        return null;
    }
}
