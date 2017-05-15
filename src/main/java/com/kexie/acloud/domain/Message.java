package com.kexie.acloud.domain;

import java.util.Date;

/**
 * Created : wen
 * DateTime : 2017/5/12 1:38
 * Description : 会议消息实体
 */
public class Message {

    // 消息属于哪个房间
    private Room room;

    // 发布者
    private User mPublisher;

    // 发送时间
    private Date time;

    // 发送的内容
    private String message;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getPublisher() {
        return mPublisher;
    }

    public void setPublisher(User publisher) {
        mPublisher = publisher;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"room\":")
                .append(room);
        sb.append(",\"mPublisher\":")
                .append(mPublisher);
        sb.append(",\"time\":\"")
                .append(time).append('\"');
        sb.append(",\"message\":\"")
                .append(message).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
