package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Task;

import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/4/25 19:09
 * Description :
 */
public interface ITaskDao {

    List<Task> getAllTask();

    void add(Task task);

    void update(Task task);

    void archive(int taskId);

    void delete(int taskId);
}
