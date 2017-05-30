package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyApply;
import com.kexie.acloud.domain.User;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:41
 * Description :
 */
@Transactional
@Rollback(false)
public class SocietyDaoTest extends com.kexie.acloud.dao.BaseTest {
    @Test
    public void addAddApply() throws Exception {
        societyDao.addApply(new SocietyApply("123", 1, "测试申请"));
    }

    @Test
    public void addMember() throws Exception {
        societyDao.addMember(1, "123");
        System.out.println(societyDao.getSocietyById(1));
    }

    @Test
    public void getSocietiesBySchoolId() throws Exception {
        System.out.println(societyDao.getSocietiesBySchoolId(1));
    }

    @Test
    public void getSocietiesByName() throws Exception {
        System.out.println(societyDao.getSocietiesByName("社团"));
    }

    @Autowired
    ISocietyDao societyDao;

    @Autowired
    IUserDao userDao;

    @Test
    public void addSociety() {
        User user = userDao.getUser("zojian@qq.com");
        Society society = new Society();
        society.setName("计算机科协");
        society.setPrincipal(user);
        society.setCreateTime(new Date());
        societyDao.add(society);
    }

    @Test
    public void getSocietyById() {
        System.out.println(societyDao.getSocietyById(1));
    }

}