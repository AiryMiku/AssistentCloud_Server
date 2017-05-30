package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Meeting;
import com.kexie.acloud.domain.User;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

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

    @Override
    public List<Meeting> getCurrentUserNotStartMeeting(String userId) {

        User user = new User(userId);

        List<Meeting> meetings = getHibernateTemplate().execute(session -> {
            Query<Meeting> query = session.createQuery("from Meeting where ? in elements(room.member) ");
            query.setParameter(0, user);

            List<Meeting> list = query.list();

//            Hibernate.initialize();

            return list;
        });

        return meetings;
    }
}
