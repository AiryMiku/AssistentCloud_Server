package com.kexie.acloud.service;

import com.kexie.acloud.dao.ITaskDao;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created : wen
 * DateTime : 2017/4/27 10:00
 * Description :
 */

@Service
public class TaskService implements ITaskService {

    @Autowired
    ITaskDao mTaskDao;

    @Override
    public Task getTaskByTaskId(String taskId) {
        return null;
    }

    @Override
    public List<Task> getTaskBySocietyId(String societyId) {
        return null;
    }

    @Override
    public List<Task> getTaskByUserId(String userId) {
        return null;
    }

    @Override
    public List<Task> getTaskByPublisherId(String publisherId) {
        return mTaskDao.getTasksByPublisherId(publisherId);
    }

    @Override
    public void create(Task task) {

    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void updateProgress(String taskId, double progress) {

    }

    @Override
    public void updateSubTask(String taskId, Map<String, Double> subTask) {

    }

    @Override
    public void updateExecutor(String taskId, List<User> executors) {

    }

    @Override
    public void archive(String taskId) {

    }

    @Override
    public void delete(String taskId) {

    }
}
