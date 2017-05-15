package com.kexie.acloud.controller;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.domain.Message;
import com.kexie.acloud.domain.Room;
import com.kexie.acloud.domain.UploadMessage;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.log.Log;
import com.kexie.acloud.service.IIMService;
import com.kexie.acloud.service.IMeetingService;
import com.kexie.acloud.service.IUserService;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/5/11 23:12
 * Description :
 */
@Component
public class MeetingHandler implements WebSocketHandler {

    // 用户信息：userId - User
    private static final Map<String, User> mUserCache = new ConcurrentHashMap<>();
    // 正在房间里面的用户: roomId - User
    private static final Map<Integer, List<User>> mRoom = new ConcurrentHashMap<>();
    // 用户WebSocketSession: UserId - session
    private static final Map<String, WebSocketSession> mUserWsSession = new ConcurrentHashMap<>();

    @Resource
    private IUserService mUserService;

    @Resource
    private IMeetingService mMeetingService;

    @Resource
    private IIMService mIMService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String userId = (String) session.getAttributes().get("userId");
        Integer roomId = new Integer((String) session.getAttributes().get("roomId"));

        if (roomId == null) {
            // js可以在event中获取到错误信息
            session.close(new CloseStatus(1007, "没有携带房间号"));
            return;
        }

        User user = mUserService.getUserByUserId(userId);
        // 每次请求缓存用户信息,所以不需要判断当前用户是否已经访问过
        mUserCache.put(userId, user);
        // 将这个用户保存到房间中
        if (mRoom.get(roomId) == null) {
            List<User> users = new ArrayList<>();
            users.add(user);
            mRoom.put(roomId, users);
        } else {
            if (!mRoom.get(roomId).contains(user)) {
                mRoom.get(roomId).add(user);
            }
        }
        // 保存这个Session
        mUserWsSession.putIfAbsent(userId, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        UploadMessage pushMessage = JSON.parseObject(message.getPayload().toString(), UploadMessage.class);

        Log.debug("获取到的消息 " + pushMessage);

        int roomId = pushMessage.getRoomId();
        Room room = mIMService.getRoomInfo(roomId);

        if (room == null) {
            session.close(new CloseStatus(1007, "没有当前房间"));
            return;
        }

        User user = mUserCache.get(pushMessage.getUserId());

        Message result = new Message();
        result.setRoom(room);
        result.setPublisher(user);
        result.setTime(new Date());
        result.setMessage(pushMessage.getMessage());

        List<User> users = mRoom.get(roomId);

        // 推送到其他用户上
        for (User item : users) {
            WebSocketSession targetSession = mUserWsSession.get(item.getUserId());

            if (targetSession != null && targetSession.isOpen() && targetSession != session)
                targetSession.sendMessage(new TextMessage(JSON.toJSONString(result)));
        }
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("错误信息");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        String sessionId = session.getId();
        System.out.println("关闭的id = " + sessionId + "  状态：" + closeStatus);

        Set<String> keySet = mUserWsSession.keySet();
        for (String userId : keySet) {

            WebSocketSession s = mUserWsSession.get(userId);
            // 通过id找到这个Session，然后remove

            if (s.getId().equals(sessionId)) {
                s.close(closeStatus);
                mUserWsSession.remove(userId);
                return;
            }
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
