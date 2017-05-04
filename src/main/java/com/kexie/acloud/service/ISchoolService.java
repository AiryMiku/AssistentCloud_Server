package com.kexie.acloud.service;

import com.kexie.acloud.domain.College;
import com.kexie.acloud.domain.Major;
import com.kexie.acloud.domain.School;

import java.io.File;
import java.util.List;

/**
 * Created by zojian on 2017/4/26.
 */
public interface ISchoolService {

    //==========学校================
    public List<School> getAllSchool();

    public School getSchoolById(int id);

    public School getSchoolByName(String name);

    public boolean schoolHasExists(String name);

    public boolean addSchool(School school);

    public void updateSchool(School school,String name);

    public void deleteSchool(int id);

    public void addSchoolsFromExcel(File file);

    //==========学院================
    public College getCollegeById(int id);

    public College getCollegeByName(String name,int school_id);

    public boolean collegeHasExists(String name,int school_id);

    public List<College> getAllCollege(int school_id);

    public boolean addCollege(College college,int school_id);

    public void deleteCollege(int college_id);

    //==========专业================
    Major getMajorById(int id);

    Major getMajorByName(String name,int college_id);

    boolean majorHasExists(String name,int college_id);

    List<Major> getAllMajor(int college_id);

    boolean addMajor(Major major,int college_id);

    void deleteMajor(int major_id);
}
