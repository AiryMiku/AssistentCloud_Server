package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.kexie.acloud.domain.JsonSerializer.UserIdListDeserializer;
import com.kexie.acloud.domain.JsonSerializer.UserIdListSerializer;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

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
import javax.persistence.OneToMany;

/**
 * Created : wen
 * DateTime : 2017/5/12 1:57
 * Description :
 */
@Entity
public class Room {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    // 名字
    private String name;

    // 房主
    @ManyToOne
    private User master;

    // 群成员
    @JoinTable(name = "room_member",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JSONField(serializeUsing = UserIdListSerializer.class, deserializeUsing = UserIdListDeserializer.class)
    private List<User> member;

    // 房间类型（-1:未指定 , 0：会议房间，1：多人聊天）
    private int type = -1;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public List<User> getMember() {
        return member;
    }

    public void setMember(List<User> member) {
        this.member = member;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"roomId\":")
                .append(roomId);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"master\":")
                .append(master);
        sb.append(",\"member\":")
                .append(member);
        sb.append(",\"type\":")
                .append(type);
        sb.append('}');
        return sb.toString();
    }
}
