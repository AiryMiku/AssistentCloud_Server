package com.kexie.acloud.dao;

import com.kexie.acloud.controller.AdminController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zojian on 2017/4/26.
 */
public class SchoolDaoTest extends com.kexie.acloud.dao.BaseTest {
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    AdminController adminController;

    @Test
    public void getSchoolById(){
        System.out.println(schoolDao.getSchoolById(1).toString());
    }

    @Test
    public void getAllSchoool(){
        assert schoolDao.getAllSchool().size()>0;
    }

    @Test
    public void uploadSchoolExcel(){
        //File file = new File("/Users/zhuangzhongjian/Desktop/AssistentCloud_Server/src/main/test/school_excel.xls");
        //System.out.println(adminController.addSchools(file));
    }


}
