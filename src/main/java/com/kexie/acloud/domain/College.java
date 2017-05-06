package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 学院表
 * Created by zojian on 2017/4/27.
 */
@Entity
public class College {
    //学院ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "college_id")
    @JSONField(ordinal = 1)
    private int id;

    //学院名称
    @JSONField(ordinal = 2)
    @Column(name = "college_name")
    private String name;

    //所属学校（多对一）
    @JSONField(serialize = false,deserialize = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "school_id",nullable = false)
    private School school;

    //专业集合（一对多）
    @JSONField(ordinal = 3)
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "college_id")
    private Set<Major> majors = new HashSet<Major>();

    // 社团集合（一对多）
    @JSONField(ordinal = 4)
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "college_id")
    private Set<Society> societies = new HashSet<Society>();

    public College() {}

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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<Major> getMajors() {
        return majors;
    }

    public void setMajors(Set<Major> majors) {
        this.majors = majors;
    }

    public Set<Society> getSocieties() {
        return societies;
    }

    public void setSocieties(Set<Society> societies) {
        this.societies = societies;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("College{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", school=").append(school);
        sb.append(", majors=").append(majors);
        sb.append(", societies=").append(societies);
        sb.append('}');
        return sb.toString();
    }
}
