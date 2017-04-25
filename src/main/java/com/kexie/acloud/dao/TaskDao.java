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
public class TaskDao extends HibernateDaoSupport implements ITaskDao {

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public void add(Task task) {
        getHibernateTemplate().save(task);
    }

    @Override
    public void update(Task task) {
        getHibernateTemplate().update(task);
    }

    @Override
    public void archive(int taskId) {
        Task task = getHibernateTemplate().get(Task.class, taskId);

        // TODO: 2017/4/25 设置归档

        getHibernateTemplate().update(task);
    }

    @Override
    public void delete(int taskId) {
        Task task = new Task();
        task.setId(taskId);
        getHibernateTemplate().delete(taskId);
    }
}
