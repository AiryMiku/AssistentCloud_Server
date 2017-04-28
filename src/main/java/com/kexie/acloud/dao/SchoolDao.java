package com.kexie.acloud.dao;

import com.kexie.acloud.domain.School;
import com.kexie.acloud.util.ExcelUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.IllegalFormatException;
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


    @Override
    public School getSchoolById(int id) {
        return getCurrentSession().get(School.class,id);
    }

    @Override
    public School getSchoolByName(String name) {
        String hql = "FROM School WHERE school_name = ?";
        Query query = getCurrentSession().createQuery(hql);
        List<School> result = (List<School>) query.setParameter(0,name).list();
        if(result.size()>0){
            return result.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public List<School> getAllSchool() {
        String hql = "FROM School  ORDER BY school_name";
        //return (List<School>) hibernateTemplate.find(hql);
        return (List<School>) getCurrentSession().createQuery(hql).list();
    }

    @Override
    public boolean schoolHasExists(String name) {
        return getSchoolByName(name)==null?false:true;
    }

    @Override
    public void addSchool(School school) {
        getCurrentSession().save(school);
    }

    @Override
    public void updateSchool(School school,String name) {
        school.setName(name);
        getCurrentSession().update(school);
    }

    @Override
    public void deleteSchool(int id) {
        School school = getSchoolById(id);
        if(school!=null)
            getCurrentSession().delete(school);
    }

    @Override
    public void addSchoolsFromExcel(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            List<School> schoolList = ExcelUtil.getSchoolExcelInfo(is,ExcelUtil.isExcel2003(file.getPath()));
            for (School school:schoolList) {
                addSchool(school);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
