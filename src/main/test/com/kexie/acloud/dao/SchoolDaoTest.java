package com.kexie.acloud.dao;

import com.kexie.acloud.domain.College;
import com.kexie.acloud.domain.Major;
import com.kexie.acloud.domain.School;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Created by zojian on 2017/4/26.
 */
public class SchoolDaoTest extends com.kexie.acloud.dao.BaseTest {

    @Autowired
    ISchoolDao schoolDao;


    @Test
    public void getSchoolById(){
        System.out.println(schoolDao.getSchoolById(2));
    }

    @Test
    public void getSchoolByName(){
        assert schoolDao.getSchoolByName("zhbitt")!=null;
    }

    @Test
    public void SchoolExists(){
        assert schoolDao.schoolHasExists("zhbitt")==true;
        assert schoolDao.schoolHasExists("qwe")==false;
    }

    @Test
    public void getAllSchoool(){
        assert schoolDao.getAllSchool().size()>0;
    }


    @Test
    public void deleteSchool(){
        schoolDao.deleteSchool(4);
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
    public void addMajor(){
        College college = schoolDao.getCollegeById(1);
        Major major1 = new Major();
        major1.setName("软件工程");
        major1.setCollege(college);
        //college.getMajors().add(major1);

        Major major2 = new Major();
        major2.setName("网络工程");
        major2.setCollege(college);
        //college.getMajors().add(major2);

        schoolDao.addCollege(college,college.getSchool().getId());
        schoolDao.addMajor(major1,major1.getCollege().getId());
        schoolDao.addMajor(major2,major2.getCollege().getId());
        System.out.println(college.toString());
    }


}
