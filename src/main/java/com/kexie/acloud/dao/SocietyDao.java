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
    private SessionFactory sessionFactory;

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    /**
     * 通过社团名字和社团所在学校id判断是否已经注册
     * @param name
     * @param school_id
     * @return
     */
    public boolean societyHasExists(String name,int school_id){
        return false;
    }

    /**
     * 通过ID查询社团信息
     * @param id
     * @return
     */
    public Society getSocietyById(int id){
        return getCurrentSession().get(Society.class,id);
    }

    /**
     * 添加社团
     * @param society
     * @return
     */
    public boolean addSociety(Society society){
        if(societyHasExists(society.getName(),society.getSchool().getId())==false) {
            getCurrentSession().save(society);
            return true;
        }
        else{
            return false;
        }
    }
}
