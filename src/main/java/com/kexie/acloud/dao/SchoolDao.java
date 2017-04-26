package com.kexie.acloud.dao;

import com.kexie.acloud.domain.School;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zojian on 2017/4/25.
 */
@Repository
@Transactional
public class SchoolDao implements ISchoolDao {
    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Override
    public School getSchoolById(int id) {
        return hibernateTemplate.get(School.class,id);
    }

    @Override
    public School getSchoolByName(String name) {
        return null;
    }

    @Override
    public List<School> getAllSchool() {
        String hql = "FROM School  ORDER BY school_name";
        //return (List<School>) hibernateTemplate.find(hql);
        return (List<School>) getCurrentSession().createQuery(hql).list();
    }

    @Override
    public boolean SchoolExists(String name) {
        return false;
    }

    @Override
    public void addSchool(School school) {
        getCurrentSession().save(school);
    }

    @Override
    public void deleteSchool(int id) {

    }
}
