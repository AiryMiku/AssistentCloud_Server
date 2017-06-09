package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.kexie.acloud.domain.JsonSerializer.SocietyDeserializer;
import com.kexie.acloud.domain.JsonSerializer.SocietySerializer;
import com.kexie.acloud.domain.JsonSerializer.UserDeserializer;
import com.kexie.acloud.domain.JsonSerializer.UserSerializer;

import javax.persistence.*;

/**
 * Created : wen
 * DateTime : 2017/5/30 19:03
 * Description : 社团申请实体
 */
@Entity(name = "society_apply")
public class SocietyApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    // 申请人
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JSONField(ordinal = 1,serializeUsing = UserSerializer.class, deserializeUsing = UserDeserializer.class)
    public User user;

    // 申请社团
    @ManyToOne
    @JoinColumn(name = "society_id")
    @JSONField(ordinal = 2,serializeUsing = SocietySerializer.class, deserializeUsing = SocietyDeserializer.class)
    public Society society;

    // 申请原因
    public String reason;

    public SocietyApply() {
    }

    public SocietyApply(String userId, int societyId, String reason) {
        this.user = new User(userId);
        this.society = new Society();
        this.society.setId(societyId);
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
