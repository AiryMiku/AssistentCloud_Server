package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:40
 * Description :
 */
@Repository
@Transactional
public class SocietyDao implements ISocietyDao{

    @Autowired
    SessionFactory sessionFactory;

    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean add(Society society) {
        try {
            getCurrentSession().save(society);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Society getSocietyById(int society_id) {
        return getCurrentSession().get(Society.class,society_id);
    }
}
