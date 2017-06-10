package com.kexie.acloud.dao;


import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyPosition;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.util.BeanUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    public User updateUser(User u) {
        User user = getHibernateTemplate().load(User.class, u.getUserId());
        BeanUtil.copyProperties(u, user);
        getHibernateTemplate().flush();
        getHibernateTemplate().clear();
        return getHibernateTemplate().get(User.class, u.getUserId());
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

        List<SocietyPosition> positions = (List<SocietyPosition>) getHibernateTemplate().find("from society_position where society.id = ?", society_id);

        List<User> users = (List<User>) getHibernateTemplate().find("from User");

        List<User> result = new ArrayList<>();

        users.forEach(user -> {
            for (SocietyPosition position : user.getSocietyPositions()) {
                for (SocietyPosition societyPosition : positions) {
                    if (position.getId() == societyPosition.getId()) {
                        result.add(user);
                        return;
                    }
                }
            }
        });

        return result;
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

    /**
     * 模糊搜索用户
     *
     * @param query
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUserBySearch(String query) {
        String search = "%" + query + "%";
        return (List<User>) getHibernateTemplate()
                .find("from User where userId like ? or nickName like ?",
                        search, search);
    }
}
