package com.kexie.acloud.dao;

import com.kexie.acloud.domain.SubTask;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.util.BeanUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:30
 * Description :
 */
@Repository

public class TaskDao extends HibernateDaoSupport implements ITaskDao {

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> getTasksByPublisherId(String publishId) {
        // Can not set java.lang.String field com.kexie.acloud.domain.User.userId to java.lang.String
        // 原因：publisher.userId 直接写成了 publisher
        return (List<Task>) getHibernateTemplate()
                .find("from Task where publisher.userId = ?", publishId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> getTasksByUserId(String userId) {
        User user = new User();
        user.setUserId(userId);
        // 查询啊,好难
        return (List<Task>) getHibernateTemplate()
                .find("from Task  where ? in elements(executors) ", user);
    }

    @Override
    public Task getTasksByTaskId(String taskId) {
        return getHibernateTemplate().get(Task.class, taskId);
    }

    public void add(Task task) {
        getHibernateTemplate().save(task);
    }

    @Override
    public Task update(Task task) {
        Task t = getHibernateTemplate().get(Task.class, task.getId());
        BeanUtil.copyProperties(task, t);
        getHibernateTemplate().update(t);
        return getHibernateTemplate().get(Task.class, task.getId());
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        getHibernateTemplate().update(subTask);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> getTasksBySocietyId(int societyId) {
        return (List<Task>) getHibernateTemplate()
                .find("from Task where society.id = ?", societyId);
    }

    //    @Override
//    public void active(String taskId) {
//        Task task = getHibernateTemplate().get(Task.class, taskId);
//        task.setTaskType(1);
//        getHibernateTemplate().update(task);
//    }
//
//    @Override
//    public void archive(String taskId) {
//        Task task = getHibernateTemplate().get(Task.class, taskId);
//        task.setTaskType(2);
//        getHibernateTemplate().update(task);
//    }
//
//    @Override
//    public void delete(String taskId) {
//        Task task = getHibernateTemplate().get(Task.class, taskId);
//        task.setTaskType(3);
//        getHibernateTemplate().update(task);
//    }
}
