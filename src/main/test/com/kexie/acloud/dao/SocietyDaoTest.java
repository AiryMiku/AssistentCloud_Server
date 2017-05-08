package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:41
 * Description :
 */
public class SocietyDaoTest extends com.kexie.acloud.dao.BaseTest {

    @Autowired
    ISocietyDao societyDao;

    @Autowired
    IUserDao userDao;

    @Test
    public void addSociety(){
        User user = userDao.getUser("zojian@qq.com");
        Society society = new Society();
        society.setName("计算机科协");
        society.setPrincipal(user);
        society.setCreateTime(new Date());
        societyDao.add(society);
    }

    @Test
    public void getSocietyById(){
        System.out.println(societyDao.getSocietyById(1));
    }

}