package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Task;

/**
 * Created : wen
 * DateTime : 2017/4/25 19:09
 * Description :
 */
public interface ITaskDao {

    void add(Task task);

    void update(Task task);

    void archive(int taskId);

    void delete(int taskId);
}
