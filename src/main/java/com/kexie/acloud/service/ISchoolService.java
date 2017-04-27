package com.kexie.acloud.service;

import com.kexie.acloud.domain.School;

import java.io.File;
import java.util.List;

/**
 * Created by zojian on 2017/4/26.
 */
public interface ISchoolService {

    public List<School> getAllSchool();

    public School getSchoolById(int id);

    public School getSchoolByName(String name);

    public boolean schoolHasExists(String name);

    public void addSchool(School school);

    public void deleteSchool(int id);

    public void addSchoolsFromExcel(File file);
}
