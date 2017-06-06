package com.kexie.acloud.dao;

import com.kexie.acloud.domain.SubTask;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.util.BeanUtil;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.RedisUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:30
 * Description :
 */
@Repository

public class TaskDao extends HibernateDaoSupport implements ITaskDao {

    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

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
        // 向所有任务参与者发送新任务通知
//        RedisUtil.sendMsg(jedisConnectionFactory.getJedis(),task.getExecutors(),"task",task.getTitle());
    }

    @Override
    public Task update(Task task) {

        Task t = getHibernateTemplate().load(Task.class, task.getId());

        // 清除
        getHibernateTemplate().clear();

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

    @Override
    public boolean isInExecutor(String taskId, String userId) {
        Task task = getHibernateTemplate().get(Task.class, taskId);

        if (userId.equals(task.getPublisher().getUserId()))
            return true;

        List<User> users = task.getExecutors();
        for (User user : users) {
            if (userId.equals(user.getUserId()))
                return true;
        }

        return false;
    }
}
