package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.kexie.acloud.domain.JsonSerializer.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 公告表
 * Created by zojian on 2017/5/8.
 */
@Entity
public class Notice {

    //公告ID
    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notice_id")
    @JSONField(ordinal = 0)
    private int id;

    //公告标题
    @NotBlank(message = "你忘记写公告标题了")
    @Column(name = "notice_title",nullable = false)
    @JSONField(ordinal = 1)
    private String title;

    //公告内容(TEXT)
    @NotBlank(message = "你忘记写公告内容了")
    @Type(type = "text")
    @Column(name = "notice_content",nullable = false)
    @JSONField(ordinal = 2)
    private String content;

    //公告发布者
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JSONField(ordinal = 4,serializeUsing = UserSerializer.class, deserializeUsing = UserDeserializer.class)
    private User publisher;

    //公告所属社团
    @NotNull(message = "缺少所属社团信息")
    @ManyToOne
    @JoinColumn(name = "society_id", nullable = false)
    @JSONField(ordinal = 3, serializeUsing = SocietySerializer.class, deserializeUsing = SocietyDeserializer.class)
    private Society society;

    //公告发布时间
    @JSONField(ordinal = 5)
    private Date time = new Date();

    //公告可见用户
    @JoinTable(name = "notice_user_permission",
            joinColumns = {@JoinColumn(name = "notice_id")},
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JSONField(ordinal = 6, serializeUsing = UserIdListSerializer.class, deserializeUsing = UserIdListDeserializer.class)
    private List<User> executors;

    //公告状态 (0:可显示   1： 已删除)
    @Column(name = "notice_status")
    private short status;

    // 公告查看状态(0:未被所有公告可见者查看  1： 被所有可见者查看)
    @Column
    private short visitor_status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<User> getExecutors() {
        return executors;
    }

    public void setExecutors(List<User> executors) {
        this.executors = executors;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getVisitor_status() {
        return visitor_status;
    }

    public void setVisitor_status(short visitor_status) {
        this.visitor_status = visitor_status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Notice{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", publisher=").append(publisher);
        sb.append(", society=").append(society);
        sb.append(", time=").append(time);
        sb.append(", executors=").append(executors);
        sb.append('}');
        return sb.toString();
    }
}
