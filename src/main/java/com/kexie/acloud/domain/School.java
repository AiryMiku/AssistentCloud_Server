package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 学校表
 * Created by zojian on 2017/4/25.
 */
@Entity
public class School{

    //学校ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    @JSONField(ordinal = 1)
    private int id;

    //学校名称
    @Column(name = "school_name",unique = true,nullable = false)
    @JSONField(ordinal = 2)
    private String name;

    //学院集合（一对多）
    @JSONField(serialize = false,deserialize = false)
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "school_id")
    Set<College> colleges = new HashSet<College>();

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", colleges=" + colleges +
                '}';
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

    public Set<College> getColleges() {
        return colleges;
    }

    public void setColleges(Set<College> colleges) {
        this.colleges = colleges;
    }
}
