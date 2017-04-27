package com.kexie.acloud.dao;

import com.kexie.acloud.domain.School;

import java.io.File;
import java.util.List;

/**
 * Created by zojian on 2017/4/25.
 */
public interface ISchoolDao {

    School getSchoolById(int id);

    School getSchoolByName(String name);

    List<School> getAllSchool();

    boolean schoolHasExists(String name);

    void addSchool(School school);

    void deleteSchool(int id);

    void addSchoolsFromExcel(File file);
}
