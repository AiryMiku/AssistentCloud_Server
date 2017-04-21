package com.kexie.acloud.dao;


import com.kexie.acloud.domain.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/8 14:22
 * Description :
 */
@Component("UserDao")
public class UserDaoImpl implements UserDao {

    @Resource
    private SessionFactory mSessionFactory;

    @Override
    public User getUser(String userId) {
        Session session = mSessionFactory.openSession();

        User user = session.get(User.class, userId);

        session.close();

        return user;
    }

    @Override
    public boolean hasUserById(String userId) {

        Session session = mSessionFactory.openSession();

        // 原来之前选错数据库了
        Query<User> query = session.createQuery("from User u where u.userId = ?",User.class)
                .setParameter(0,userId);

        return query.list().size() != 0;
    }

    @Override
    public void addUser(User user) {
        Session session = mSessionFactory.openSession();

        Transaction transition = session.beginTransaction();

        session.save(user);

        transition.commit();
        session.close();
    }

    @Override
    public void updateUser(User user) {
        Session session = mSessionFactory.openSession();

        session.beginTransaction();

        session.update(user);

        session.getTransaction().commit();
        session.close();
    }

    public void clear() {
        Session session = mSessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        session.createQuery("delete User").executeUpdate();

        transaction.commit();
        session.close();
    }
}
