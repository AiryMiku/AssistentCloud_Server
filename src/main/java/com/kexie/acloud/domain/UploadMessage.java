package com.kexie.acloud.domain;

import java.util.Date;

/**
 * Created : wen
 * DateTime : 2017/5/12 2:09
 * Description :
 */
public class UploadMessage {

    // 房间号
    private int roomId;

    // 发布者
    private String userId;

    // 发送的内容
    private String message;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"roomId\":")
                .append(roomId);
        sb.append(",\"userId\":\"")
                .append(userId).append('\"');
        sb.append(",\"message\":\"")
                .append(message).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
