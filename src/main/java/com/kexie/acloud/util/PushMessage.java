package com.kexie.acloud.util;

import java.util.Date;

/**
 * Created by zojian on 2017/6/2.
 */
public class PushMessage {
    private String id;
    private String identifier;
    private String publisher;
    private String logo;
    private Date time;
    private String title;
    private String info;
    private String msgType;

    public PushMessage(String identifier, String msgType, String id, String publisher, String logo, Date time, String title, String message) {
        this.id = id;
        this.msgType = msgType;
        this.publisher = publisher;
        this.logo = logo;
        this.time = time;
        this.title = title;
        this.info = info;
        this.identifier = identifier;
    }

    public PushMessage() {
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
