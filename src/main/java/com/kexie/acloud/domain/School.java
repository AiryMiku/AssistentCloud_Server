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
    //@JSONField(serialize = false,deserialize = false,ordinal=3)
    @JSONField(ordinal = 3)
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "school_id")
    Set<College> colleges = new HashSet<College>();

    // 社团集合（一对多）
    @JSONField(ordinal = 4)
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "school_id")
    private Set<Society> societies = new HashSet<Society>();

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

    public Set<Society> getSocieties() {
        return societies;
    }

    public void setSocieties(Set<Society> societies) {
        this.societies = societies;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("School{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", colleges=").append(colleges);
        sb.append(", societies=").append(societies);
        sb.append('}');
        return sb.toString();
    }
}
