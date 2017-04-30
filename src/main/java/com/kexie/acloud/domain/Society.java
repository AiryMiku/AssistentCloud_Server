package com.kexie.acloud.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created : wen
 * DateTime : 2017/4/24 20:00
 * Description : 社团实体
 */
@Entity
public class Society {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "society_id")
    private int id;

    // 社团名字
    @Column(name = "society_name")
    private String name;

    // 社团介绍
    @Column(name = "society_summary")
    private String summary;

    // 社团负责人
    @ManyToOne
    @JoinColumn(name = "principal_id")
    private User principal;

    // 创建时间
    @Column(name = "creation_time")
    private Date createTime;

    // 学院
    @ManyToOne
    private College college;

    // 社团logo
    @Column(name = "society_logo")
    private String society_logo;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public User getPrincipal() {
        return principal;
    }

    public void setPrincipal(User principal) {
        this.principal = principal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSociety_logo() {
        return society_logo;
    }

    public void setSociety_logo(String society_logo) {
        this.society_logo = society_logo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"summary\":\"")
                .append(summary).append('\"');
        sb.append(",\"principal\":")
                .append(principal);
        sb.append(",\"createTime\":\"")
                .append(createTime).append('\"');
        sb.append(",\"society_logo\":\"")
                .append(society_logo).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
