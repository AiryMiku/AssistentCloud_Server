package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Meeting;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.RedisUtil;
import com.kexie.acloud.util.SendPushMsgRunnable;
import com.kexie.acloud.util.SendRealTImePushMsgRunnable;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
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
    TaskExecutor taskExecutor;

    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        Serializable s = getHibernateTemplate().save(meeting);

//        // 向所有在线的参与者发送新会议通知
//        taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
//                "meeting",
//                meeting.getMeetingId(),
//                meeting.getName(),
//                meeting.getMembers()));
//        // 向所有参与者发送新会议通知
//        taskExecutor.execute(new SendPushMsgRunnable(jedisConnectionFactory.getJedis(),
//                "meeting",
//                meeting.getMeetingId(),
//                meeting.getName(),
//                meeting.getMembers()));
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
