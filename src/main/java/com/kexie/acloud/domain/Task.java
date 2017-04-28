package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SelectBeforeUpdate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
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

    private String title;

    // 发布者
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JSONField(serializeUsing = UserSerializer.class, deserializeUsing = UserDeserializer.class)
    private User publisher;

    // FIXME: 2017/4/28 用户不属于这个社团的也可以插入,应该怎么做约束
    // 所属社团
    @ManyToOne
    @JoinColumn(name = "society_id", nullable = false)
    // 配置只返回id，使用FastJson的序列化
    @JSONField(serializeUsing = SocietySerializer.class, deserializeUsing = SocietyDeserializer.class)
    private Society society;

    private int taskNum;

    private Date time;

    // 总进度
    private double sumProgress;

    // 涉及的干事
    @JoinTable(name = "task_user_permission",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<User> executors;

    // 遇到过这样的问题:
    // Use of @OneToMany or @ManyToMany targeting an unmapped class: com.kexie.acloud.domain.Task.subTask[java.lang.Double]
    //
    // 原因：因为当时是使用map保存<问题，进度>的，进度使用的是Double，Double没有使用@Entity注解，所以使用不了
    //      而且这样并不好，因为如果之后子问题需要扩充，就需要改了。
    //      而且遇到上面的问题，所以使用一个实体SubTask保存子任务
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "task_subTask", joinColumns = @JoinColumn(name = "task_id"))
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SubTask> subTask;

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

    public List<SubTask> getSubTask() {
        return subTask;
    }

    public void setSubTask(List<SubTask> subTask) {
        this.subTask = subTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
