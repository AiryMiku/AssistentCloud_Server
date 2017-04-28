package com.kexie.acloud.dao;

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
     * 获取一个任务信息
     *
     * @param taskId
     * @return
     */
    Task getTasksByTaskId(int taskId);

    void add(Task task);

    void update(Task task);

    void archive(int taskId);

    void delete(int taskId);
}
