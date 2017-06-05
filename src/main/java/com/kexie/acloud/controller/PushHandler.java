package com.kexie.acloud.controller;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zojian on 2017/6/5.
 */
@Component("PushHandler")
public class PushHandler implements WebSocketHandler {
    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

    // 用户WebSocketSession: UserId - session
    private static final Map<String, WebSocketSession> mUserWsSession = new ConcurrentHashMap<>();

    public static Map<String, WebSocketSession> getmUserWsSession() {
        return mUserWsSession;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String userId = (String) webSocketSession.getAttributes().get("userId");
        mUserWsSession.put(userId,webSocketSession);
        System.out.println("PushHandler afterConnectionEstablished userId:"+userId);
        if(RedisUtil.hasOfflineMsg(jedisConnectionFactory.getJedis(),userId)) {
            webSocketSession.sendMessage(new TextMessage(
                    JSON.toJSONString(
                            RedisUtil.getAllOfflineMsg(
                                    jedisConnectionFactory.getJedis(),
                                    userId))));
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        String sessionId = webSocketSession.getId();
        Set<String> keySet = mUserWsSession.keySet();
        for (String userId : keySet) {
            WebSocketSession s = mUserWsSession.get(userId);
            // 通过id找到这个Session，然后remove
            if (s.getId().equals(sessionId)) {
                s.close(closeStatus);
                mUserWsSession.remove(userId);
                System.out.println("PushHandler afterConnectionClosed userId:"+webSocketSession.getAttributes().get("userId"));
                break;
            }
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
