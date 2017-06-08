package com.kexie.acloud.util;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.controller.PushHandler;
import com.kexie.acloud.domain.User;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zojian on 2017/6/5.
 */
public class SendRealTImePushMsgRunnable implements Runnable {
    private Jedis conn;

    private int id = 0;

    private String sid;

    private String info;

    private String msgType;

    private List<User>recipients;

    private Map<String, WebSocketSession> webSocketSessionMap;


    @Override
    public void run() {
        Map<String,Object> message = null;
        if(id!=0) {
            message = RedisUtil.generateMessage(msgType, id, info, recipients);
        }
        else{
            message = RedisUtil.generateMessage(msgType,sid,info, recipients);
        }
        for (User user:recipients){
            WebSocketSession session = webSocketSessionMap.get(user.getUserId());
            String userId = user.getUserId();
            if(session!=null){
                if(session.isOpen()){
                    try {
                        session.sendMessage(new TextMessage(JSON.toJSONString(message)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    // 保存离线消息
                    RedisUtil.addOfflineMsg(conn,userId,message);
                }
            }
            else{
                // 保存离线消息
                RedisUtil.addOfflineMsg(conn,userId,message);
            }
        }
    }

    public SendRealTImePushMsgRunnable(Jedis conn, String msgType, int id, String info, List<User> recipients) {
        this.conn = conn;
        this.id = id;
        this.info = info;
        this.msgType = msgType;
        this.recipients = recipients;
        this.webSocketSessionMap = PushHandler.getmUserWsSession();
    }

    public SendRealTImePushMsgRunnable(Jedis conn, String msgType, String id, String info, List<User> recipients) {
        this.conn = conn;
        this.sid = id;
        this.info = info;
        this.msgType = msgType;
        this.recipients = recipients;
        this.webSocketSessionMap = PushHandler.getmUserWsSession();
    }
}
