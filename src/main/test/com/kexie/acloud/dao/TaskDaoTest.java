package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:33
 * Description :
 */
public class TaskDaoTest {

    static ClassPathXmlApplicationContext context;

    static TaskDao taskDao;

    @BeforeClass
    public static void before() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        taskDao = (TaskDao) context.getBean("taskDao");
    }

    @Test
    public void add() throws Exception {
        Task task = new Task();
        User user = new User();
        Society society = new Society();

        society.setId(1);
        user.setUserId("admin");

        task.setPublisher(user);
        task.setSociety(society);
        taskDao.add(task);
    }

}