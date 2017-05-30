package com.kexie.acloud.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
    public User user;

    // 申请社团
    @ManyToOne
    @JoinColumn(name = "society_id")
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
