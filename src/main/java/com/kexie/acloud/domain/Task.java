package com.kexie.acloud.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

/**
 * Created : wen
 * DateTime : 2017/4/24 17:04
 * Description :
 */
@Entity
public class Task {

    // 自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int id;

    // 发布者
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private User publisher;

    // 所属社团
    @ManyToOne
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;

    private int taskNum;

    private Date time;

    // 总进度
    private double sumProgress;

    // 涉及的干事
    @JoinTable(name = "task_user_permission",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @OneToMany(fetch = FetchType.EAGER)
    private List<User> executors;

    // 子任务-进度
    @ElementCollection
    // 表名
    @JoinTable(name = "subTask", joinColumns = @JoinColumn(name = "task_id"))
    // 子任务的进度
    @Column(name = "progress",columnDefinition = "double default 0.0")
    // 问题
    @MapKeyColumn(name = "question")
    // FIXME: 2017/4/27 解决Hibernate lazy的问题
    private Map<String, Double> subTask;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getSumProgress() {
        return sumProgress;
    }

    public void setSumProgress(double progress) {
        this.sumProgress = progress;
    }

    public List<User> getExecutors() {
        return executors;
    }

    public void setExecutors(List<User> executors) {
        this.executors = executors;
    }

    public Map<String, Double> getSubTask() {
        return subTask;
    }

    public void setSubTask(Map<String, Double> subTask) {
        this.subTask = subTask;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"publisher\":")
                .append(publisher);
        sb.append(",\"society\":")
                .append(society);
        sb.append(",\"taskNum\":")
                .append(taskNum);
        sb.append(",\"time\":\"")
                .append(time).append('\"');
        sb.append(",\"sumProgress\":")
                .append(sumProgress);
        sb.append(",\"executors\":")
                .append(executors);
        sb.append(",\"subTask\":")
                .append(subTask);
        sb.append('}');
        return sb.toString();
    }
}
