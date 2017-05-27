package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.kexie.acloud.domain.JsonSerializer.SocietyDeserializer;
import com.kexie.acloud.domain.JsonSerializer.SocietySerializer;
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
import javax.persistence.Transient;

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
    @JSONField(serializeUsing = SocietySerializer.class, deserializeUsing = SocietyDeserializer.class)
    private Society society;

    // 会议发起者
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JSONField(serializeUsing = UserSerializer.class, deserializeUsing = UserDeserializer.class)
    private User publisher;

    // 会议时间
    private Date meetingTime;

    // 聊天室房间,包括干事
    @OneToOne
    private Room room;

    // 开会成员,间接保存
    @JSONField(serialize = false, deserializeUsing = UserIdListDeserializer.class)
    @Transient
    private List<User> members;

    // 开会问题
    @JoinTable(name = "meeting_querys",
            joinColumns = {@JoinColumn(name = "meeting_id")},
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(value = FetchMode.SUBSELECT)
    @JSONField(serializeUsing = UserIdListSerializer.class, deserializeUsing = UserIdListDeserializer.class)
    private List<MeetingQuestion> questions;

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"meetingId\":")
                .append(meetingId);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"theme\":\"")
                .append(theme).append('\"');
        sb.append(",\"society\":")
                .append(society);
        sb.append(",\"publisher\":")
                .append(publisher);
        sb.append(",\"meetingTime\":\"")
                .append(meetingTime).append('\"');
        sb.append(",\"room\":")
                .append(room);
        sb.append(",\"members\":")
                .append(members);
        sb.append(",\"questions\":")
                .append(questions);
        sb.append('}');
        return sb.toString();
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<MeetingQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<MeetingQuestion> questions) {
        this.questions = questions;
    }
}
