package com.kexie.acloud.dao;

import com.kexie.acloud.config.AppConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created : wen
 * DateTime : 2017/5/6 13:04
 * Description :
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserDaoTest {

    @Autowired
    IUserDao userDao;

    @Test
    public void getUser() throws Exception {
        System.out.println(
                userDao.getUser("abc")
        );
    }

}