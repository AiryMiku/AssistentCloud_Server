package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.User;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:41
 * Description :
 */
public class SocietyDaoTest {

    static ClassPathXmlApplicationContext context;
    static SocietyDao societyDao;

    @BeforeClass
    public static void before() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        societyDao = (SocietyDao) context.getBean("societyDao");
    }

    @Test
    public void add() throws Exception {
        Society society = new Society();
        User user = new User();
        user.setUserId("admin");

        society.setName("科协 弟哥哥");
        society.setPrincipal(user);

        societyDao.add(society);
    }

}