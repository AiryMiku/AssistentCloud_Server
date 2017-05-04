package com.kexie.acloud.dao;

import com.kexie.acloud.domain.College;
import com.kexie.acloud.domain.Major;
import com.kexie.acloud.domain.School;

import java.io.File;
import java.util.List;

/**
 * Created by zojian on 2017/4/25.
 */
public interface ISchoolDao {

    //学校
    School getSchoolById(int id);

    School getSchoolByName(String name);

    List<School> getAllSchool();

    boolean schoolHasExists(String name);

    boolean addSchool(School school);

    void updateSchool(School school,String name);

    void deleteSchool(int id);

    void addSchoolsFromExcel(File file);

    //学院
    College getCollegeById(int id);

    College getCollegeByName(String name,int school_id);

    boolean collegeHasExists(String name,int school_id);

    List<College> getAllCollege(int school_id);

    boolean addCollege(College college,int school_id);

    void deleteCollege(int college_id);

    // 专业
    Major getMajorById(int id);

    Major getMajorByName(String name,int college_id);

    boolean majorHasExists(String name,int college_id);

    List<Major> getAllMajor(int college_id);

    boolean addMajor(Major major,int college_id);

    void deleteMajor(int major_id);
}
