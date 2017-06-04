package com.kexie.acloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kexie.acloud.domain.Message;
import com.kexie.acloud.domain.Room;
import com.kexie.acloud.domain.UploadMessage;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.UserException;
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
import java.util.stream.Collectors;

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
    // 正在房间里面的用户: roomId - userId
    private static final Map<Integer, List<String>> mRoomMember = new ConcurrentHashMap<>();
    // 用户WebSocketSession: UserId - session
    private static final Map<String, WebSocketSession> mUserWsSession = new ConcurrentHashMap<>();

    @Resource(name = "UserService")
    private IUserService mUserService;

    @Resource
    private IIMService mIMService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String userId = (String) session.getAttributes().get("userId");
        String id = (String) session.getAttributes().get("roomId");

        // 缓存房间信息
        Integer roomId = new Integer(id);
        Room room = mIMService.getRoomInfo(roomId);
        // TODO: 2017/6/4 房间权限，是否有权进入这个房间
        if (room == null) {
            session.close(new CloseStatus(1007, "房间号" + roomId + "不存在"));
            return;
        }
        mRoomCache.putIfAbsent(session.getId(), room);
        // 每次请求缓存用户信息
        User user = mUserService.getUserByUserId(userId);
        mUserCache.put(session.getId(), user);
        // 更新房间成员
        updateRoomMember(roomId, userId);
        // 保存这个Session
        mUserWsSession.putIfAbsent(userId, session);
        // 通知有新成员加入
        notifyRoomMemberHasNewMember(roomId, user, session);
    }

    /**
     * 新成员加入一个房间之后，向新成员推送房间成员和向房间成员推送新成员
     *
     * @param roomId
     * @param newMember
     * @param newMemberSession
     * @throws IOException
     * @throws UserException
     */
    private void notifyRoomMemberHasNewMember(Integer roomId, User newMember, WebSocketSession newMemberSession) throws IOException, UserException {
        // 向房间的人推送新成员
        List<String> users = mRoomMember.get(roomId);
        for (String userId : users) {

            if (userId.equals(newMember.getUserId())) continue;

            WebSocketSession socketSession = mUserWsSession.get(userId);

            // 新成员
            JSONObject object = new JSONObject();
            object.put("userId", newMember.getUserId());
            object.put("logo", newMember.getLogoUrl());
            object.put("nickName", newMember.getNickName());
            object.put("type", 2);

            socketSession.sendMessage(new TextMessage(object.toJSONString()));
        }

        // 向新成员推送房间的人
        JSONArray array = new JSONArray();
        for (String userId : users) {

            if (userId.equals(newMember.getUserId())) continue;

            // TODO: 2017/6/4 每次都向数据库取数据，之后改为Redis
            User user = mUserService.getUserByUserId(userId);

            JSONObject object = new JSONObject();
            object.put("userId", user.getUserId());
            object.put("logo", user.getLogoUrl());
            object.put("nickName", user.getNickName());

            array.add(object);
        }

        JSONObject json = new JSONObject();
        json.put("type",3);
        json.put("data",array);
        newMemberSession.sendMessage(new TextMessage(json.toJSONString()));
    }

    /**
     * 向房间中添加一个聊天成员
     *
     * @param roomId 房间号
     * @param userId 新成员的用户Id
     */
    private void updateRoomMember(int roomId, String userId) {
        // 将这个用户保存到房间成员中
        if (mRoomMember.get(roomId) == null) {
            List<String> users = new ArrayList<>();
            users.add(userId);
            mRoomMember.put(roomId, users);
        } else {
            List<String> users = mRoomMember.get(roomId);

            int size = users.stream()
                    .filter(u -> u.equals(userId))
                    .collect(Collectors.toList())
                    .size();

            if (size == 0) {
                mRoomMember.get(roomId).add(userId);
            }
        }

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        Log.debug("获取到的消息 " + message.getPayload().toString());

        if (!(message.getPayload() instanceof String)) return;

        String msg = (String) JSON.parseObject(message.getPayload().toString(), JSONObject.class).get("message");

        String currentUserId = (String) session.getAttributes().get("userId");
        Integer roomId = new Integer((String) session.getAttributes().get("roomId"));

        Message result = new Message();
        result.setRoom(roomId);
        result.setPublisher(currentUserId);
        result.setTime(new Date());
        result.setMessage(msg);
        result.setType(1);

        // 该房间的所有用户
        List<String> users = mRoomMember.get(roomId);

        // 推送到其他用户上
        for (String userId : users) {
            WebSocketSession targetSession = mUserWsSession.get(userId);

            if (targetSession != null && targetSession.isOpen() && targetSession != session)
                targetSession.sendMessage(new TextMessage(JSON.toJSONString(result)));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Log.debug("错误信息");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        String sessionId = session.getId();

        Log.debug("关闭的id = " + sessionId + "  状态：" + closeStatus);

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
                List<String> users = mRoomMember.get(room.getRoomId());
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).equals(u.getUserId())) {
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
