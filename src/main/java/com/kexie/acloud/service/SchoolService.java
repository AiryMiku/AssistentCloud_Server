package com.kexie.acloud.service;

import com.kexie.acloud.dao.ISchoolDao;
import com.kexie.acloud.domain.College;
import com.kexie.acloud.domain.Major;
import com.kexie.acloud.domain.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * Created by zojian on 2017/4/26.
 */
@Service
@Transactional
public class SchoolService implements ISchoolService {

    @Autowired
    private ISchoolDao schoolDao;

    @Override
    public List<School> getAllSchool() {
        return schoolDao.getAllSchool();
    }

    @Override
    public School getSchoolById(int id) {
        return schoolDao.getSchoolById(id);
    }

    @Override
    public School getSchoolByName(String name) {
        return schoolDao.getSchoolByName(name);
    }

    @Override
    public boolean schoolHasExists(String name) {
        return schoolDao.schoolHasExists(name);
    }

    @Override
    public boolean addSchool(School school) {
        return schoolDao.addSchool(school);
    }

    @Override
    public void updateSchool(School school, String name) {
        schoolDao.updateSchool(school, name);
    }

    @Override
    public void deleteSchool(int id) {
        schoolDao.deleteSchool(id);
    }

    @Override
    public void addSchoolsFromExcel(File file) {
        schoolDao.addSchoolsFromExcel(file);
    }

    @Override
    public College getCollegeById(int id) {
        return schoolDao.getCollegeById(id);
    }

    @Override
    public College getCollegeByName(String name, int school_id) {
        return schoolDao.getCollegeByName(name, school_id);
    }

    @Override
    public boolean collegeHasExists(String name, int school_id) {
        return schoolDao.collegeHasExists(name, school_id);
    }

    @Override
    public List<College> getAllCollege(int school_id) {
        return schoolDao.getAllCollege(school_id);
    }

    @Override
    public boolean addCollege(College college, int school_id){
         return schoolDao.addCollege(college, school_id);
    }

    @Override
    public void deleteCollege(int college_id) {
        schoolDao.deleteCollege(college_id);
    }

    @Override
    public Major getMajorById(int id) {
        return schoolDao.getMajorById(id);
    }

    @Override
    public Major getMajorByName(String name, int college_id) {
        return schoolDao.getMajorByName(name, college_id);
    }

    @Override
    public boolean majorHasExists(String name, int college_id) {
        return schoolDao.majorHasExists(name, college_id);
    }

    @Override
    public List<Major> getAllMajor(int college_id) {
        return schoolDao.getAllMajor(college_id);
    }

    @Override
    public boolean addMajor(Major major, int college_id) {
        return schoolDao.addMajor(major, college_id);
    }

    @Override
    public void deleteMajor(int major_id) {
        schoolDao.deleteMajor(major_id);
    }
}
