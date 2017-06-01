package com.kexie.acloud.controller.form;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SubTask;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;

import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created : wen
 * DateTime : 2017/6/1 12:35
 * Description : 创建一个任务需要上传的信息
 */
public class CreateTaskForm {

    @Length(min = 1, message = "标题不能为空,长度min = 1")
    @NotNull(message = "你没有上传title字段")
    private String title;

    @NotNull(message = "你的任务属于哪个社团啊,你不说我怎么知道呢")
    @Min(value = 1, message = "你的任务属于哪个社团啊,你不说我怎么知道呢")
    private int societyId;

    @NotNull(message = "有谁参与这个任务啊，难道就你自己一个人吗")
    @Size(min = 1,message = "有谁参与这个任务啊，难道就你自己一个人吗")
    private List<String> executors;

    @NotNull(message = "你需看要细分任务吧，需要有子任务")
    @Size(min = 1,message = "你需看要细分任务吧，需要有子任务")
    private List<String> subTask;

    /**
     * 将表单装换为Task对象
     *
     * @return
     */
    public Task toTask() {

        // 执行者
        List<User> users = new ArrayList<>();
        executors.forEach(userId -> {
            users.add(new User(userId));
        });

        // 子任务
        List<SubTask> subTasks = new ArrayList<>();
        subTask.forEach(t -> {
            subTasks.add(new SubTask(t, 0));
        });

        Task task = new Task();
        task.setTitle(title);
        task.setSociety(new Society(societyId));
        task.setExecutors(users);
        task.setSubTask(subTasks);
        return task;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"title\":\"")
                .append(title).append('\"');
        sb.append(",\"societyId\":")
                .append(societyId);
        sb.append(",\"executors\":")
                .append(executors);
        sb.append(",\"subTask\":")
                .append(subTask);
        sb.append('}');
        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSocietyId() {
        return societyId;
    }

    public void setSocietyId(int societyId) {
        this.societyId = societyId;
    }

    public List<String> getExecutors() {
        return executors;
    }

    public void setExecutors(List<String> executors) {
        this.executors = executors;
    }

    public List<String> getSubTask() {
        return subTask;
    }

    public void setSubTask(List<String> subTask) {
        this.subTask = subTask;
    }


}
