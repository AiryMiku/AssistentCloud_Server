package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Notice;
import com.kexie.acloud.util.BeanUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zojian on 2017/5/8.
 */
@Repository
@Transactional
public class NoticeDao extends HibernateDaoSupport implements INoticeDao {
    @Autowired
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public boolean addNotice(Notice notice) {
        try {
            getHibernateTemplate().save(notice);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateNotice(Notice notice) {
        try {
            Notice newNotice = getHibernateTemplate().get(Notice.class,notice.getId());
            BeanUtil.copyProperties(notice,newNotice);
            getHibernateTemplate().save(newNotice);
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
    public List<Notice> getNoticesByUserId(int user_id, int page) {
        return null;
    }

    @Override
    public List<Notice> getNoticesByPublisherId(int publisher_id, int page) {
//        String hql = "FROM Notice WHERE ";
        return null;
    }

    @Override
    public List<Notice> getNoticesByUserIdAndSocietyId(int user_id, int society_id, int page) {
        return null;
    }

    @Override
    public Notice getNoticeByNoticeId(int notice_id) {
        return null;
    }
}
