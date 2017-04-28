package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Task;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:30
 * Description :
 */
@Repository
@Transactional
public class TaskDao extends HibernateDaoSupport implements ITaskDao {

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<Task> getTasksByPublisherId(String publishId) {
        // Can not set java.lang.String field com.kexie.acloud.domain.User.userId to java.lang.String
        // 原因：publisher.userId 直接写成了 publisher
        return (List<Task>) getHibernateTemplate()
                .find("from Task where publisher.userId = ?", publishId);
    }

    @Override
    public Task getTasksByTaskId(int taskId) {
        return getHibernateTemplate().get(Task.class,taskId);
    }

    public void add(Task task) {
        getHibernateTemplate().save(task);
    }

    @Override
    public void update(Task task) {
        // 不考虑动态更新的问题了
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
