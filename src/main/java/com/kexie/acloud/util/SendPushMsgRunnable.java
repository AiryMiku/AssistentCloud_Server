package com.kexie.acloud.util;

import com.kexie.acloud.domain.User;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by zojian on 2017/6/5.
 */
public class SendPushMsgRunnable implements Runnable {
    //Jedis conn,String msgType, int id, String info,List<User> recipients
    private Jedis conn;

    private String msgType;

    private int id;

    private String sid;

    private String title;

    private String info;

    private List<User> recipients;

    public SendPushMsgRunnable(Jedis conn, String msgType, int id, String title, String info, List<User> recipients) {
        this.conn = conn;
        this.msgType = msgType;
        this.id = id;
        this.title = title;
        this.info = info;
        this.recipients = recipients;
    }

    public SendPushMsgRunnable(Jedis conn, String msgType, String id, String title, String info, List<User> recipients) {
        this.conn = conn;
        this.msgType = msgType;
        this.sid = id;
        this.title = title;
        this.info = info;
        this.recipients = recipients;
    }

    @Override
    public void run() {
        if(id!=0) {
            RedisUtil.sendPushMsg(conn, msgType, id, title, info, recipients);
        }
        else{
            RedisUtil.sendPushMsg(conn, msgType, sid, title, info, recipients);
        }
    }


    public Jedis getConn() {
        return conn;
    }

    public void setConn(Jedis conn) {
        this.conn = conn;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<User> recipients) {
        this.recipients = recipients;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
