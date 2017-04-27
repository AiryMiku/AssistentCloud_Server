package com.kexie.acloud.service;

import com.kexie.acloud.dao.ISchoolDao;
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
    public void addSchool(School school) {
        schoolDao.addSchool(school);
    }

    @Override
    public void updateSchool(School school, String name) {
        schoolDao.updateSchool(school,name);
    }

    @Override
    public void deleteSchool(int id) {
        schoolDao.deleteSchool(id);
    }

    @Override
    public void addSchoolsFromExcel(File file) {
        schoolDao.addSchoolsFromExcel(file);
    }
}
