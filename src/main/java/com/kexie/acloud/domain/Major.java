package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

/**
 * 专业表
 * Created by zojian on 2017/4/28.
 */
@Entity
public class Major {
    //专业ID
    @JSONField(ordinal = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private int id;

    //专业名称
    @JSONField(ordinal = 2)
    @Column(name = "major_name")
    private String name;

    //所属学院（多对一）
    @JSONField(serialize = false,deserialize = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "college_id")
    private College college;

    public Major() {}

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", college=" + college +
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

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

}
