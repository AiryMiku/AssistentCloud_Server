package com.kexie.acloud.dao;


import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyPosition;
import com.kexie.acloud.domain.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/8 14:22
 * Description :
 */
@Component("UserDao")
public class UserDao extends HibernateDaoSupport implements IUserDao {

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public User getUser(String userId) {
        return getHibernateTemplate().get(User.class, userId);
    }

    @Override
    public boolean hasUserById(String userId) {

        User user = getHibernateTemplate().get(User.class, userId);

        return user != null;
    }

    @Override
    public void addUser(User user) {
        getHibernateTemplate().save(user);
    }

    @Override
    public void updateUser(User user) {
        getHibernateTemplate().update(user);
    }

    @Override
    public void clear() {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getUserBySociety(int society_id) {
        return (List<User>) getHibernateTemplate()
                .find("from User where (from society_position where society.id = ?) in elements(societyPositions)", society_id);
    }

    @Override
    public List<Society> getSocietiesByUserId(String userId) {
        User user = getHibernateTemplate().get(User.class, userId);

        List<SocietyPosition> societyPositions = user.getSocietyPositions();

        List<Society> result = new ArrayList<>();
        societyPositions.forEach(position -> {
            result.add(position.getSociety());
        });
        return result;
    }
}
