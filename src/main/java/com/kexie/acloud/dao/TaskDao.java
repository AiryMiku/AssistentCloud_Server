package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Task;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:30
 * Description :
 */
@Component("taskDao")
@Transactional
public class TaskDao extends HibernateDaoSupport {

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public Task add(Task task) {
        getHibernateTemplate().save(task);

        Task t = getHibernateTemplate().get(Task.class, task.getId());
        return t;
    }
}
