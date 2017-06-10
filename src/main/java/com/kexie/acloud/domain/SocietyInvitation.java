package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.kexie.acloud.domain.JsonSerializer.SocietyDeserializer;
import com.kexie.acloud.domain.JsonSerializer.SocietySerializer;
import com.kexie.acloud.domain.JsonSerializer.UserDeserializer;
import com.kexie.acloud.domain.JsonSerializer.UserSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created : wen
 * DateTime : 2017/6/10 12:36
 * Description :
 */
@Entity(name = "society_invitation")
public class SocietyInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invitationId;

    // 被邀请的人
    @ManyToOne
    @JoinColumn(name = "invita_user_id")
    @JSONField(ordinal = 1, serializeUsing = UserSerializer.class, deserializeUsing = UserDeserializer.class)
    private User invitaUser;

    // 谁发起的邀请
    @ManyToOne
    @JoinColumn(name = "handle_user_id")
    @JSONField(ordinal = 1, serializeUsing = UserSerializer.class, deserializeUsing = UserDeserializer.class)
    private User handleUser;

    // 社团
    @ManyToOne
    @JoinColumn(name = "society_id")
    @JSONField(ordinal = 2, serializeUsing = SocietySerializer.class, deserializeUsing = SocietyDeserializer.class)
    private Society society;

    @ManyToOne
    @JoinColumn(name = "position_id")
    @JSONField(ordinal = 3)
    private SocietyPosition position;

    // 申请原因
    private String message;

    public SocietyInvitation() {

    }

    public SocietyInvitation(String inviteUserId, String handleUserId, int societyId, int positionId, String message) {
        this.invitaUser = new User(inviteUserId);
        this.handleUser = new User(handleUserId);
        this.society = new Society(societyId);
        this.message = message;
        this.position = new SocietyPosition(positionId);
    }

    public int getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(int inviteId) {
        this.invitationId = inviteId;
    }

    public User getInvitaUser() {
        return invitaUser;
    }

    public void setInvitaUser(User invitaUser) {
        this.invitaUser = invitaUser;
    }

    public User getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(User handleUser) {
        this.handleUser = handleUser;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public SocietyPosition getPosition() {
        return position;
    }

    public void setPosition(SocietyPosition position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
