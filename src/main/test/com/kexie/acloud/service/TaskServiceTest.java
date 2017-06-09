package com.kexie.acloud.service;

import com.kexie.acloud.config.AppConfig;
import com.kexie.acloud.dao.ITaskDao;
import com.kexie.acloud.domain.Task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created : wen
 * DateTime : 2017/5/1 1:17
 * Description :
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TaskServiceTest {

    @Autowired
    ITaskService taskDao;

    @Test
    public void update() throws Exception {
        Task t =  taskDao.getTaskByTaskId("4028b8815bba5116015bba5120590000","","");
        t.setTitle("更新之后的值");
        taskDao.update(t);
    }

}