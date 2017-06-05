package com.kexie.acloud.util;

import java.util.Date;

/**
 * Created by zojian on 2017/6/2.
 */
public class PushMessage {
    private int id;
    private String identifier;
    private Date time;
    private String title;
    private String message;

    public PushMessage(String identifier,int id, Date time, String title, String message) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.message = message;
        this.identifier = identifier;
    }

    public PushMessage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
