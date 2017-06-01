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
@Component("MeetingHandler")
public class MeetingHandler implements WebSocketHandler {

    // 用户信息：userId - User
    private static final Map<String, User> mUserCache = new ConcurrentHashMap<>();
    // 房间缓存
    private static final Map<String, Room> mRoomCache = new ConcurrentHashMap<>();
    // 正在房间里面的用户: roomId - User
    private static final Map<Integer, List<User>> mRoomMember = new ConcurrentHashMap<>();
    // 用户WebSocketSession: UserId - session
    private static final Map<String, WebSocketSession> mUserWsSession = new ConcurrentHashMap<>();

    @Resource(name = "UserService")
    private IUserService mUserService;

    @Resource
    private IMeetingService mMeetingService;

    @Resource
    private IIMService mIMService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String userId = (String) session.getAttributes().get("userId");
        String id = (String) session.getAttributes().get("roomId");
        if (id == null || "".equals(id)) {
            // js可以在event中获取到错误信息
            session.close(new CloseStatus(1007, "没有携带房间号"));
            return;
        }

        // 缓存房间信息
        Integer roomId = new Integer(id);
        Room room = mIMService.getRoomInfo(roomId);
        if (room == null) {
            session.close(new CloseStatus(1007, "房间号 不存在"));
            return;
        }
        mRoomCache.put(session.getId(), room);

        // 每次请求缓存用户信息
        User user = mUserService.getUserByUserId(userId);
        mUserCache.put(session.getId(), user);

        // 将这个用户保存到房间中
        if (mRoomMember.get(roomId) == null) {
            List<User> users = new ArrayList<>();
            users.add(user);
            mRoomMember.put(roomId, users);
        } else {
            List<User> users = mRoomMember.get(roomId);
            boolean hasUser = false;
            for (User u : users) {
                if (u.getUserId().equals(user.getUserId())) {
                    hasUser = true;
                    break;
                }
            }
            if (!hasUser) {
                mRoomMember.get(roomId).add(user);
            }
        }

        // 保存这个Session
        mUserWsSession.putIfAbsent(userId, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        UploadMessage pushMessage = JSON.parseObject(message.getPayload().toString(), UploadMessage.class);

        Log.debug("获取到的消息 " + pushMessage);

        String sessionId = session.getId();

        Room room = mRoomCache.get(sessionId);
        User user = mUserCache.get(sessionId);

        Message result = new Message();
        result.setRoom(room.getRoomId());
        result.setPublisher(user.getUserId());
        result.setTime(new Date());
        result.setMessage(pushMessage.getMessage());

        // 该房间的所有用户
        List<User> users = mRoomMember.get(room.getRoomId());

        // 推送到其他用户上
        for (User item : users) {
            WebSocketSession targetSession = mUserWsSession.get(item.getUserId());

            try {
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }

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

                // 移除对应的房间成员
                Room room = mRoomCache.get(sessionId);
                User u = mUserCache.get(sessionId);
                List<User> users = mRoomMember.get(room.getRoomId());
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUserId().equals(u.getUserId())) {
                        users.remove(i);
                        break;
                    }
                }
                return;
            }
        }


    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
