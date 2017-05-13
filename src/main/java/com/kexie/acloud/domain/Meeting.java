package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.kexie.acloud.domain.JsonSerializer.UserDeserializer;
import com.kexie.acloud.domain.JsonSerializer.UserIdListDeserializer;
import com.kexie.acloud.domain.JsonSerializer.UserIdListSerializer;
import com.kexie.acloud.domain.JsonSerializer.UserSerializer;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created : wen
 * DateTime : 2017/5/12 10:47
 * Description :
 */

@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meetingId;

    // 会议名字
    private String name;

    // 会议主题
    private String theme;

    // 所属社团
    @ManyToOne
    private Society society;

    // 会议发起者
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JSONField(serializeUsing = UserSerializer.class, deserializeUsing = UserDeserializer.class)
    private User publisher;

    // 会议时间
    private Date meetingTime;

    // 聊天室房间
    @OneToOne
    private Room room;

    // 开会干事
    @JoinTable(name = "meeting_user_permission",
            joinColumns = {@JoinColumn(name = "meeting_id")},
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(value = FetchMode.SUBSELECT)
    @JSONField(serializeUsing = UserIdListSerializer.class, deserializeUsing = UserIdListDeserializer.class)
    private List<User> executors;

    public List<User> getExecutors() {
        return executors;
    }

    public void setExecutors(List<User> executors) {
        this.executors = executors;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }
}
