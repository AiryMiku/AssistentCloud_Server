package com.kexie.acloud.domain;

import java.util.Date;

/**
 * Created : wen
 * DateTime : 2017/5/12 1:38
 * Description : 会议消息实体
 */
public class Message {

    // 消息属于哪个房间
    private int room;

    // 发布者
    private String mPublisher;

    // 发送时间
    private Date time;

    // 发送的内容
    private String message;

    // 消息类型
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public void setPublisher(String publisher) {
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
