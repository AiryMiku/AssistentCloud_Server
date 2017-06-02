package com.kexie.acloud.util;

import com.kexie.acloud.domain.User;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by zojian on 2017/6/2.
 */
public class SendMsgThread implements Runnable {

    private Jedis conn;

    private String msg;

    private String msgType;

    private List<User>executors;

    public SendMsgThread(Jedis conn, String msg, String msgType, List<User> executors) {
        this.conn = conn;
        this.msg = msg;
        this.msgType = msgType;
        this.executors = executors;
    }

    public SendMsgThread() {
    }

    @Override
    public void run() {
        RedisUtil.sendMsg(conn,executors,msgType,msg);
    }
}
