package com.kexie.acloud.util;

import java.util.Date;

/**
 * Created by zojian on 2017/6/2.
 */
public class PushMessage {
    private String id;
    private String identifier;
    private Date time;
    private String title;
    private String info;

    public PushMessage(String identifier,String id, Date time, String title, String message) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.info = info;
        this.identifier = identifier;
    }

    public PushMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}