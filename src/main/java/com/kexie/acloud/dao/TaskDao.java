package com.kexie.acloud.dao;

import com.kexie.acloud.domain.SubTask;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.util.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
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
    TaskExecutor taskExecutor;

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
    public Task getTasksByTaskId(String taskId, String userId, String identifier) {
        if(identifier!=null){
            RedisUtil.deleteMsg(jedisConnectionFactory.getJedis(),
                    userId,
                    identifier,
                    "task");
        }
        return getHibernateTemplate().get(Task.class, taskId);
    }

    public void add(Task task) {
        getHibernateTemplate().save(task);
        // 向所有在线的参与者发送新任务通知
        taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                task.getId(),
                "你有一条新的任务通知，快去查看吧❤️",
                task.getTitle(),
                task.getExecutors()));
        // 向所有参与者发送新任务通知
        taskExecutor.execute(new SendPushMsgRunnable(jedisConnectionFactory.getJedis(),
                "task",
                task.getId(),
                "你有一条新的任务通知，快去查看吧❤️",
                task.getTitle(),
                task.getExecutors()));

    }

    @Override
    public Task update(Task task) {

        Task t = getHibernateTemplate().load(Task.class, task.getId());

        // 清除
        getHibernateTemplate().clear();

        BeanUtil.copyProperties(task, t);
        getHibernateTemplate().update(t);

        // 向所有在线的参与者发送新任务通知
        taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                task.getId(),
                "你有一条任务更新了，快去查看吧❤️",
                task.getTitle(),
                task.getExecutors()));
        // 向所有参与者发送新任务通知
        taskExecutor.execute(new SendPushMsgRunnable(jedisConnectionFactory.getJedis(),
                "task",
                task.getId(),
                "你有一条任务更新了，快去查看吧❤️",
                task.getTitle(),
                task.getExecutors()));

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
