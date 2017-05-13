package com.kexie.acloud.websocket;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.log.Log;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;


/**
 * Socket处理器
 *
 * @author Goofy
 * @Date 2015年6月11日 下午1:19:50
 */
//@Component
public class MyWebSocketHandler implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

//    // 如果实现app和web同时推送的话
//    // 我会用redis保存app的session
//    // 在内存中保存web的session
//    public static final Map<Long, WebSocketSession> userSocketSessionMap;
////    public static final
//
//    static {
//        userSocketSessionMap = new HashMap<>();
//    }
//
//    /**
//     * 建立连接后
//     */
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session)
//            throws Exception {
//
//        Log.debug("建立连接之后");
//
//        Long uid = (Long) session.getAttributes().get("uid");
//
//
//        if (userSocketSessionMap.get(uid) == null) {
//            Log.debug("保存当前用户的Session");
//            userSocketSessionMap.put(uid, session);
//        }
//    }
//
//    /**
//     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
//     */
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//
//        Log.debug("处理消息");
//
//        // 获取到消息长度
//        // 这个message是客户端指定的格式吗
//        Log.debug(message.getPayload().toString());
//
//        if (message.getPayloadLength() == 0)
//            return;
//
//        // 构建一个Message
//        Message msg = JSON.parseObject(message.getPayload().toString(), Message.class);
//        msg.setDate(new Date());
//
//        sendMessageToUser(msg.getTo(), new TextMessage(JSON.toJSONString(msg)));
//    }
//
//    /**
//     * 消息传输错误处理
//     */
//    @Override
//    public void handleTransportError(WebSocketSession session,
//                                     Throwable exception) throws Exception {
//
//        Log.debug("处理错误信息");
//
//        if (session.isOpen()) {
//            session.close();
//        }
//        Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap
//                .entrySet().iterator();
//        // 移除Socket会话
//        while (it.hasNext()) {
//            Entry<Long, WebSocketSession> entry = it.next();
//            if (entry.getValue().getId().equals(session.getId())) {
//                userSocketSessionMap.remove(entry.getKey());
//                Log.debug("Socket 会话已经移除:用户ID" + entry.getKey());
//                break;
//            }
//        }
//    }
//
//    /**
//     * 关闭连接后
//     */
//    @Override
//    public void afterConnectionClosed(WebSocketSession session,
//                                      CloseStatus closeStatus) throws Exception {
//
//        Log.debug("Websocket:" + session.getId() + "已经关闭");
//
//        Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap
//                .entrySet().iterator();
//        // 移除Socket会话
//        while (it.hasNext()) {
//            // UserId - WebSocketSession
//            Entry<Long, WebSocketSession> entry = it.next();
//
//            // 找到当前退回连接的UserId ，然后移除他的WebSocketSession
//            if (entry.getValue().getId().equals(session.getId())) {
//
//                userSocketSessionMap.remove(entry.getKey());
//
//                Log.debug("Socket会话已经移除:用户ID" + entry.getKey());
//                break;
//
//            }
//        }
//    }
//
//    @Override
//    public boolean supportsPartialMessages() {
//        return false;
//    }
//
//    /**
//     * 给所有在线用户发送消息
//     *
//     * @param message
//     * @throws IOException
//     */
//    public void broadcast(final TextMessage message) throws IOException {
//
//        Log.debug("群发信息");
//
//        Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap
//                .entrySet().iterator();
//
//        // 多线程群发
//        while (it.hasNext()) {
//
//            final Entry<Long, WebSocketSession> entry = it.next();
//
//            if (entry.getValue().isOpen()) {
//                // entry.getValue().sendMessage(message);
//                new Thread(() -> {
//                    try {
//                        if (entry.getValue().isOpen()) {
//                            entry.getValue().sendMessage(message);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }).start();
//            }
//
//        }
//    }
//
//    /**
//     * 给某个用户发送消息
//     *
//     * @param uid
//     * @param message
//     * @throws IOException
//     */
//    public void sendMessageToUser(Long uid, TextMessage message)
//            throws IOException {
//
//        Log.debug("给某个用户发信息");
//
//        WebSocketSession session = userSocketSessionMap.get(uid);
//
//        // 判断这个Session是否还可以用
//        if (session != null && session.isOpen()) {
//            session.sendMessage(message);
//        }
//    }

}
