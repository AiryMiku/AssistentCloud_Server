package com.kexie.acloud.service;

import com.kexie.acloud.domain.School;

import java.util.List;

/**
 * Created by zojian on 2017/4/26.
 */
public interface ISchoolService {

    public List<School> getAllSchool();

    public School getSchoolById(int id);

    public School getSchoolByName(String name);

    public boolean SchoolExists(String name);

    public void addSchool(School school);

    public void deleteSchool(int id);

}
