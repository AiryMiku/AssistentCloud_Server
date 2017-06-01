package com.kexie.acloud.dao;

import com.kexie.acloud.domain.SubTask;
import com.kexie.acloud.domain.Task;

import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/4/25 19:09
 * Description :
 */
public interface ITaskDao {

    /**
     * 获取发布者发布的所有任务
     *
     * @param publishId 发布者 userId
     * @return 当前发布者发布的所有任务
     */
    List<Task> getTasksByPublisherId(String publishId);

    /**
     * 获取用户拥有的所有任务
     *
     * @param userId
     * @return
     */
    List<Task> getTasksByUserId(String userId);

    /**
     * 获取一个任务详细信息
     *
     * @param taskId
     * @return
     */
    Task getTasksByTaskId(String taskId);

    void add(Task task);

    Task update(Task task);

    void updateSubTask(SubTask subTask);

    List<Task> getTasksBySocietyId(int societyId);

    boolean isInExecutor(String id, String userId);
}
