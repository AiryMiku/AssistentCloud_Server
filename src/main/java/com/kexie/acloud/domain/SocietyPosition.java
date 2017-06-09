package com.kexie.acloud.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    // 职位名称
    private String name;

    // 职位高低 （ 0为最低
    private int grade = 0;

    @ManyToOne
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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
        final StringBuffer sb = new StringBuffer("SocietyPosition{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", grade=").append(grade);
        sb.append(", society=").append(society);
        sb.append('}');
        return sb.toString();
    }
}
