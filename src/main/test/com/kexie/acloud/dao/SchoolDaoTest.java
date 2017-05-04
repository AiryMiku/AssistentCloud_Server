package com.kexie.acloud.dao;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.domain.College;
import com.kexie.acloud.domain.Major;
import com.kexie.acloud.domain.School;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Created by zojian on 2017/4/26.
 */
public class SchoolDaoTest extends com.kexie.acloud.dao.BaseTest {

    @Autowired
    ISchoolDao schoolDao;

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void addSchool(){
        School school1 = new School();
        school1.setId(1);
        school1.setName("北京理工大学珠海学院");
        schoolDao.addSchool(school1);
        School school2 = new School();
        school2.setId(2);
        school2.setName("北京师范大学珠海学院");
        schoolDao.addSchool(school2);
    }

    @Test
    public void addSchoolJson(){
        String json = "{\"name\":\"暨南大学\"}";
        School school = JSON.parseObject(json,School.class);
        schoolDao.addSchool(school);
    }

    @Test
    public void getSchoolById(){
        //SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(School.class,"id","name");
        //System.out.println(JSON.toJSONString(schoolDao.getSchoolById(1),simplePropertyPreFilter));
        System.out.println(JSON.toJSONString(schoolDao.getSchoolById(1)));
    }

    @Test
    public void getSchoolByName(){
        assert schoolDao.getSchoolByName("北京理工大学珠海学院")!=null;
    }

    @Test
    public void SchoolExists(){
        assert schoolDao.schoolHasExists("北京理工大学珠海学院")==true;
        assert schoolDao.schoolHasExists("qwe")==false;
    }

    @Test
    public void getAllSchoool(){
        assert schoolDao.getAllSchool().size()>0;
        System.out.println(JSON.toJSONString(schoolDao.getAllSchool()));
    }


    @Test
    public void deleteSchool(){
        schoolDao.deleteSchool(3);
    }

    @Test
    public void uploadSchoolExcel(){
        File file = new File("/Users/zhuangzhongjian/Desktop/AssistentCloud_Server/src/main/test/com/kexie/acloud/dao/school_excel.xlsx");
        schoolDao.addSchoolsFromExcel(file);
    }

    @Test
    public void addCollege(){
        School school = schoolDao.getSchoolById(1);
        College college1 = new College();
        college1.setName("计算机学院");
        college1.setSchool(school);
        College college2 = new College();
        college2.setName("信息学院");
        college2.setSchool(school);
        schoolDao.addCollege(college1,college1.getSchool().getId());
        schoolDao.addCollege(college2,college2.getSchool().getId());
    }

    @Test
    public void addCollegeJson(){
        String json = "{\"name\":\"艺术学院\"}";
        College college = JSON.parseObject(json, College.class);
        schoolDao.addCollege(college,1);

    }

    @Test
    public void addMajor(){
        Major major1 = new Major();
        major1.setName("软件工程");

        Major major2 = new Major();
        major2.setName("网络工程");

        schoolDao.addMajor(major1,1);
        schoolDao.addMajor(major2,1);
    }

    @Test
    public void addMajorJson(){
        String json = "{\"name\":\"树莓\"}";
        Major major = JSON.parseObject(json,Major.class);
        System.out.println(major);
    }

    @Test
    public void getAllMajors(){
        College college = schoolDao.getAllCollege(1).get(0);
       // System.out.println(JSON.toJSONString(college));
        System.out.println(JSON.toJSONString(schoolDao.getAllMajor(college.getId())));
    }


}
