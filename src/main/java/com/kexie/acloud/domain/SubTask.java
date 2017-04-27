package com.kexie.acloud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created : wen
 * DateTime : 2017/4/27 21:39
 * Description :
 */

@Entity
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String question;

    @Column(name = "progress", columnDefinition = "double default 0.0")
    private double progress;

    public SubTask(){}

    public SubTask(String question, double progress) {
        this.question = question;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"question\":\"")
                .append(question).append('\"');
        sb.append(",\"progress\":")
                .append(progress);
        sb.append('}');
        return sb.toString();
    }
}
