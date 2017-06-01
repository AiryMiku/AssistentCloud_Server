package com.kexie.acloud.controller.form;

import com.kexie.acloud.domain.SubTask;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created : wen
 * DateTime : 2017/6/1 16:42
 * Description :
 */
public class UpdateTaskForm {

    // 自增
    @NotNull(message = "没有id ，我怎么知道你更新的是哪个任务呢")
    private String id;

    // 标题
    @Length(min = 1, message = "标题不能为空,长度min = 1")
    private String title;

    // 任务类型：1:活动，2:已经归档，3:删除
    @Range(min = 1, max = 3, message = "任务类型不正确")
    private int taskType = 1;

    // 涉及的干事
    @Size(min = 1, message = "有谁参与这个任务啊，难道就你自己一个人吗")
    private List<String> executors;

    // 子任务,带上子任务id就是更新这条子任务，不带就是添加新的任务
    @Size(min = 1, message = "你需看要细分任务吧，需要有子任务")
    private List<SubTask> subTask;

    /**
     * 转换
     *
     * @return
     */
    public Task toTask() {

        // 执行者
        List<User> users = new ArrayList<>();
        executors.forEach(userId -> users.add(new User(userId)));

        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setTaskType(taskType);
        task.setExecutors(users);
        task.setSubTask(subTask);

        return task;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"title\":\"")
                .append(title).append('\"');
        sb.append(",\"taskType\":")
                .append(taskType);
        sb.append(",\"executors\":")
                .append(executors);
        sb.append(",\"subTask\":")
                .append(subTask);
        sb.append('}');
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public List<String> getExecutors() {
        return executors;
    }

    public void setExecutors(List<String> executors) {
        this.executors = executors;
    }

    public List<SubTask> getSubTask() {
        return subTask;
    }

    public void setSubTask(List<SubTask> subTask) {
        this.subTask = subTask;
    }
}
