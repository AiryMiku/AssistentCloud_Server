package com.kexie.acloud.service;

import com.kexie.acloud.dao.ITaskDao;
import com.kexie.acloud.domain.SubTask;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.TaskException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

/**
 * Created : wen
 * DateTime : 2017/4/27 10:00
 * Description :
 */

@Service
@Transactional
public class TaskService implements ITaskService {

    @Autowired
    ITaskDao mTaskDao;

    @Override
    public Task getTaskByTaskId(String taskId) {

        Task task = mTaskDao.getTasksByTaskId(taskId);

        if (task == null)
            throw new TaskException("没有找到当前任务");

        return task;
    }

    @Override
    public List<Task> getTaskBySocietyId(int societyId) {
        return mTaskDao.getTasksBySocietyId(societyId);
    }

    @Override
    public List<Task> getTaskByUserId(String userId) {
        return mTaskDao.getTasksByUserId(userId);
    }

    @Override
    public List<Task> getTaskByPublisherId(String publisherId) {
        return mTaskDao.getTasksByPublisherId(publisherId);
    }

    @Override
    public void create(Task task, String userId) {

        User user = new User();
        user.setUserId(userId);

        task.setPublisher(user);

        mTaskDao.add(task);
    }

    @Override
    public Task update(Task task) {
        return mTaskDao.update(task);
    }


    @Override
    public void updateSubTask(List<SubTask> subTasks) {
        // 更新子任务
        subTasks.forEach(task -> mTaskDao.updateSubTask(task));
    }

    @Override
    public void updateSubTask(String taskId, List<SubTask> subTask) {
        Task task = mTaskDao.getTasksByTaskId(taskId);
        task.setSubTask(subTask);
        mTaskDao.update(task);
    }

    @Override
    public void updateExecutor(String taskId, List<User> executors) {
        Task task = mTaskDao.getTasksByTaskId(taskId);
        task.setExecutors(executors);
        mTaskDao.update(task);
    }

    @Override
    public void active(String taskId) {
        Task task = mTaskDao.getTasksByTaskId(taskId);
        task.setTaskType(1);
        mTaskDao.update(task);
    }

    @Override
    public void archive(String taskId) {

        Task task = mTaskDao.getTasksByTaskId(taskId);

        if (task == null)
            throw new TaskException("没有这条数据");

        // TODO: 2017/5/8 需要判断userId是否有权修改这个任务吗

        task.setTaskType(2);
        mTaskDao.update(task);
    }

    @Override
    public void delete(String taskId) {
        Task task = mTaskDao.getTasksByTaskId(taskId);

        if (task == null)
            throw new TaskException("没有这条数据");

        task.setTaskType(3);
        mTaskDao.update(task);
    }
}
