package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

/**
 * Created : wen
 * DateTime : 2017/5/6 12:39
 * Description : 社团职位
 */
@Entity(name = "society_position")
public class SocietyPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 职位等级( 1 为最低
    @Min(value = 1, message = "社团等级错误")
    private int grade;

    // 职位名字
    @NotBlank(message = "社团职位不能为空呀")
    private String name;

    @ManyToOne
    @JoinColumn(name = "society_id", nullable = false)
    @JSONField(serialize = false)
    private Society society;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    public SocietyPosition() {
    }

    public SocietyPosition(int id) {
        this.id = id;
    }

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

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"grade\":")
                .append(grade);
        sb.append('}');
        return sb.toString();
    }
}
