package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.Task;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:40
 * Description :
 */
@Component("societyDao")
@Transactional
public class SocietyDao extends HibernateDaoSupport implements ISocietyDao{

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public Society add(Society society) {
        getHibernateTemplate().save(society);

        Society s = getHibernateTemplate().get(Society.class, society.getId());
        return s;
    }
}
