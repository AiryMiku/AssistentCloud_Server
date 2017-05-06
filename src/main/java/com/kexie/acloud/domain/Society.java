package com.kexie.acloud.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

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

    // 学校
    @ManyToOne
    private School school;

    // 社团logo
    @Column(name = "society_logo")
    private String society_logo;

    // 社团等级(0: 校级社团  1：院级社团)
    @Column(name = "society_level")
    private int level;

    //社团状态(-1:待审核 0：审核通过 1：注销)
    @Column(name = "society_status")

    //社团成员
    @OneToMany
    @JoinColumn(name = "society_id")
    private Set<User> users;

    private int status;

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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Society{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", principal=").append(principal);
        sb.append(", createTime=").append(createTime);
        sb.append(", college=").append(college);
        sb.append(", school=").append(school);
        sb.append(", society_logo='").append(society_logo).append('\'');
        sb.append(", level=").append(level);
        sb.append(", users=").append(users);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
