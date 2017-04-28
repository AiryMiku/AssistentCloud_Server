package com.kexie.acloud.dao;

import com.kexie.acloud.config.AppConfig;
import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SubTask;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:33
 * Description :
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TaskDaoTest {


    @Autowired
    ITaskDao taskDao;

    @Test
    public void add() throws Exception {
//        Task task = new Task();
//        User user = new User();
//        Society society = new Society();
//
//        society.setId(1);
//        user.setUserId("admin");
//
//        task.setPublisher(user);
//        task.setSociety(society);
//        taskDao.add(task);
    }

    @Test
    public void update() {
        Task task = new Task();

        User user = new User();
        Society society = new Society();

        society.setId(1);
        user.setUserId("admin");

        task.setPublisher(user);
        task.setSociety(society);


        List<SubTask> subTasks = new ArrayList<>();

        subTasks.add(new SubTask("问题1",0.1));

        task.setSubTask(subTasks);

        taskDao.add(task);

        System.out.println(taskDao.getAllTask());
    }

}