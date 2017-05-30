package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.kexie.acloud.domain.FormConvert.CollegeConvert;
import com.kexie.acloud.domain.JsonSerializer.CollegeDeserializer;
import com.kexie.acloud.domain.JsonSerializer.CollegeSerializer;
import com.kexie.acloud.domain.JsonSerializer.UserDeserializer;
import com.kexie.acloud.domain.JsonSerializer.UserSerializer;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created : wen
 * DateTime : 2017/4/24 20:00
 * Description : 社团实体
 */
@Entity
public class Society {

    public interface Create {
    }

    public interface Update {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "society_id")
    @Min(value = 1, groups = {Update.class}, message = "社团Id不合法")// 从零开始递增，id不可能小于1
    private int id;

    // 社团名字
    @Column(name = "society_name")
    @Length(min = 1, groups = {Create.class, Update.class}, message = "社团名字不能为空")
    private String name;

    // 社团介绍
    @Column(name = "society_summary")
    private String summary = "这个社团很懒，什么都没有说";

    // 社团负责人
    @ManyToOne
    @JoinColumn(name = "principal_id")
    @JSONField(serializeUsing = UserSerializer.class, deserializeUsing = UserDeserializer.class)
    @NotNull(groups = {Create.class}, message = "社团不能没有社团负责人")
    private User principal;

    // 创建时间
    @Column(name = "creation_time")
    @NotNull(groups = {Create.class}, message = "创建时间不能为空")
    private Date createTime;

    // 学院
    @ManyToOne
    @JSONField(serializeUsing = CollegeSerializer.class, deserializeUsing = CollegeDeserializer.class)
    @Convert(converter = CollegeConvert.class)
    @NotNull(groups = {Create.class}, message = "学院不能为空")
    @JoinColumn(name = "college_id")
    private College college;

    // 社团成员
    @ManyToMany
    @JoinTable(name = "society_member",
            joinColumns = {@JoinColumn(name = "society_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> members;

    // 社团logo
    @Column(name = "society_logo")
    private String societyLogo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
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

    public String getSocietyLogo() {
        return societyLogo;
    }

    public void setSocietyLogo(String society_logo) {
        this.societyLogo = society_logo;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
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
        sb.append(",\"college\":")
                .append(college);
        sb.append(",\"members\":")
                .append(members);
        sb.append(",\"societyLogo\":\"")
                .append(societyLogo).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
