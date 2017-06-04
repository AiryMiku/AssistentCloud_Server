package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Meeting;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.RedisUtil;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/5/12 14:18
 * Description :
 */
@Repository
public class MeetingDao extends HibernateDaoSupport implements IMeetingDao {

    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        Serializable s = getHibernateTemplate().save(meeting);
        // 向所有会议参与者发送会议通知
        RedisUtil.sendMsg(jedisConnectionFactory.getJedis(),meeting.getMembers(),"meeting",meeting.getName());
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
