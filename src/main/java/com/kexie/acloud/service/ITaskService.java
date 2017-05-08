package com.kexie.acloud.service;

import com.kexie.acloud.domain.SubTask;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;

import java.util.List;

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
    List<Task> getTaskBySocietyId(int societyId);

    /**
     * 获取用户的所有Task
     *
     * @param userId
     */
    List<Task> getTaskByUserId(String userId);

    /**
     * 获取发布者的任务
     *
     * @param publisherId
     * @return
     */
    List<Task> getTaskByPublisherId(String publisherId);

    /**
     * 创建一个任务
     *
     * @param task
     * @param userId
     */
    void create(Task task, String userId);

    /**
     * 更新task信息
     *
     * @param task
     */
    Task update(Task task);

    /**
     * 更新任务进度
     *
     * @param subTasks
     */
    void updateSubTask(List<SubTask> subTasks);


    /**
     * 更新子任务
     *
     * @param taskId  更新任务的Id
     * @param subTask 最新的子任务
     */
    void updateSubTask(String taskId, List<SubTask> subTask);

    /**
     * 更新任务的执行者
     *
     * @param taskId    更新任务的Id
     * @param executors 最新的执行者
     */
    void updateExecutor(String taskId, List<User> executors);

    void active(String taskId);

    /**
     * 归档一个Task
     *  @param taskId
     *
     */
    void archive(String taskId);

    /**
     * 删除一个Task
     *
     * @param taskId
     */
    void delete(String taskId);


}
