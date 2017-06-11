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

    private String publisher;

    private String logo;

    private String title;

    private String info;

    private List<User> recipients;

    public SendPushMsgRunnable(Jedis conn, String msgType, int id, String publisher, String logo, String title, String info, List<User> recipients) {
        this.conn = conn;
        this.msgType = msgType;
        this.id = id;
        this.publisher = publisher;
        this.logo = logo;
        this.title = title;
        this.info = info;
        this.recipients = recipients;
    }

    public SendPushMsgRunnable(Jedis conn, String msgType, String id, String publisher, String logo, String title, String info, List<User> recipients) {
        this.conn = conn;
        this.msgType = msgType;
        this.sid = id;
        this.publisher = publisher;
        this.logo = logo;
        this.title = title;
        this.info = info;
        this.recipients = recipients;
    }

    @Override
    public void run() {
        if (id != 0) {
            RedisUtil.sendPushMsg(conn, msgType, id, publisher, logo, title, info, recipients);
        } else {
            RedisUtil.sendPushMsg(conn, msgType, sid, publisher, logo, title, info, recipients);
        }
    }
}
