package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyPosition;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.util.BeanUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:40
 * Description :
 */
@Repository
public class SocietyDao extends HibernateDaoSupport implements ISocietyDao {

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public Society getSocietyById(int society_id) {
        return getHibernateTemplate().get(Society.class, society_id);
    }

    @Override
    public List<Society> getSocietiesBySchoolId(int schoolId) {
        return (List<Society>) getHibernateTemplate().find("from Society where college.school.id = ?", schoolId);
    }

    @Override
    public List<Society> getSocietiesByCollegeId(int collegeId) {
        return (List<Society>) getHibernateTemplate().find("from Society where college.id = ?", collegeId);
    }

    @Override
    public void update(Society society) {
        Society s = getHibernateTemplate().get(Society.class, society.getId());
        BeanUtil.copyProperties(society, s);
        getHibernateTemplate().update(s);
    }

    @Override
    public List<Society> getSocietiesByName(String query) {
        return (List<Society>) getHibernateTemplate().find("from Society  where name like ?", "%" + query + "%");
    }

    @Override
    public SocietyPosition getSocietyPositionByUserId(User user, Society society) {
        User u = getHibernateTemplate().get(User.class, user.getUserId());
        for (SocietyPosition position : u.getSocietyPositions()) {
            if (position.getSociety().getId() == society.getId())
                return position;
        }
        return null;
    }

    @Override
    public boolean isInSociety(Society society, User member) {
        User u = getHibernateTemplate().get(User.class, member.getUserId());
        for (SocietyPosition position : u.getSocietyPositions()) {
            if (position.getSociety().getId() == society.getId())
                return true;
        }
        return false;
    }

    @Override
    public boolean hasSociety(String societyName, int collegeId) {
        return getHibernateTemplate()
                .find("from Society where name = ? and college.id = ?",
                        societyName, collegeId)
                .size() > 0;
    }

    @Override
    public void add(Society society) {
        getHibernateTemplate().save(society);
    }


}
