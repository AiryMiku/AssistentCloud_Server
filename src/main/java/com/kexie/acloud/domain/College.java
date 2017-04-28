package com.kexie.acloud.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zojian on 2017/4/27.
 */
@Entity
@Table(name = "College")
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "college_id")
    private int id;

    @Column(name = "college_name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "school_id",nullable = false)
    private School school;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "college_id")
    private Set<Major> majors = new HashSet<Major>();

    public College() {
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

    @Override
    public String toString() {
        return "College{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", majors=" + majors +
                '}';
    }
}
