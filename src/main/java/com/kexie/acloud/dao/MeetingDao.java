package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Meeting;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/5/12 14:18
 * Description :
 */
@Repository
public class MeetingDao extends HibernateDaoSupport implements IMeetingDao {

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        Serializable s = getHibernateTemplate().save(meeting);
        System.out.println(s);
    }
}
