package com.kexie.acloud.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created : wen
 * DateTime : 2017/4/24 17:04
 * Description :
 */
@Entity
public class Task {

    @Id
    // 自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    public int id;

    // 发布者
    @ManyToOne
    @JoinColumn(name = "publisher_id" , nullable = false)
    public User publisher;

    // 所属社团
    @ManyToOne
    @JoinColumn(name = "society_id",nullable = false)
    public Society society;


    public int taskNum;

    public Date time;

    public double progress;


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

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
