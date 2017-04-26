package com.kexie.acloud.domain;

import javax.persistence.*;

/**
 * Created by zojian on 2017/4/25.
 */
@Entity
@Table(name = "School")
public class School{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "school_id")
    private int id;

    @Column(name = "school_name")
    private String name;

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

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
