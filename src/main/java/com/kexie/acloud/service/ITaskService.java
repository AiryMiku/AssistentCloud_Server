package com.kexie.acloud.service;

import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Created : wen
 * DateTime : 2017/4/27 9:59
 * Description :
 */
public interface ITaskService {

    /**
     * 通过id获取到Task的详细信息
     *
     * @param taskId
     */
    Task getTaskByTaskId(String taskId);

    /**
     * 获取社团的所有Task
     *
     * @param societyId
     */
    List<Task> getTaskBySocietyId(String societyId);

    /**
     * 获取用户的所有Task
     *
     * @param userId
     */
    List<Task> getTaskByUserId(String userId);

    /**
     * 创建一个任务
     *
     * @param task
     */
    void create(Task task);

    /**
     * 更新task信息
     *
     * @param task
     */
    void update(Task task);

    /**
     * 更新任务进度
     *
     * @param taskId   更新的任务Id
     * @param progress 任务进度
     */
    void updateProgress(String taskId, double progress);


    /**
     * 更新子任务
     *
     * @param taskId  更新任务的Id
     * @param subTask 最新的子任务
     */
    void updateSubTask(String taskId, Map<String, Double> subTask);

    /**
     * 更新任务的执行者
     *
     * @param taskId    更新任务的Id
     * @param executors 最新的执行者
     */
    void updateExecutor(String taskId, List<User> executors);

    /**
     * 归档一个Task
     *
     * @param taskId
     */
    void archive(String taskId);

    /**
     * 删除一个Task
     *
     * @param taskId
     */
    void delete(String taskId);
}
