package com.kexie.acloud.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kexie.acloud.config.AppConfig;
import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SubTask;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

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
        Task task = new Task();
        Task t2 = new Task();

        // 任务发布者
        User publisher = new User();
        publisher.setUserId("wen");
        task.setPublisher(publisher);
        t2.setPublisher(publisher);

        // 任务属于的Id
        Society society = new Society();
        society.setId(1);
        task.setSociety(society);
        t2.setSociety(society);

        // 子任务
        List<SubTask> subTasks = new ArrayList<>();
        subTasks.add(new SubTask("问题1", 0.1));
        task.setSubTask(subTasks);

        List<SubTask> subTasks2 = new ArrayList<>();
        subTasks2.add(new SubTask("问题1", 0.1));
        subTasks2.add(new SubTask("问题2", 0.2));
        subTasks2.add(new SubTask("问题3", 0.3));
        t2.setSubTask(subTasks2);

        taskDao.add(task);
        taskDao.add(t2);

        List<Task> tasksByPublisherId = taskDao.getTasksByPublisherId(publisher.getUserId());

        // 处理游离化问题
        System.out.println(
                JSON.toJSONString(tasksByPublisherId, SerializerFeature.DisableCircularReferenceDetect));
    }

    @Test
    public void update() {
        Task task = taskDao.getTasksByTaskId("123");
        System.out.println(task);
        System.out.println(JSON.toJSONString(task));

        String s = "{\"executors\":[],\"id\":2,\"publisher\":\"wen\",\"society\":1,\"subTask\":[{\"id\":2,\"progress\":0.1,\"question\":\"问题1，是否能更新啊\"},{\"id\":3,\"progress\":0.2,\"question\":\"问题2\"},{\"id\":4,\"progress\":0.3,\"question\":\"问题3\"}],\"sumProgress\":0.0,\"taskNum\":0,\"time\":1493410104238,\"title\":\"123\"}";

        Task x = JSON.parseObject(s, Task.class);
        System.out.println(x);

        taskDao.update(x);
    }

    @Test
    public void getTasksByUserId() throws Exception {

        Task task = new Task();
        Task t2 = new Task();

        // 任务发布者
        User publisher = new User();
        publisher.setUserId("admin");
        task.setPublisher(publisher);
        t2.setPublisher(publisher);

        // 任务属于社团的Id
        Society society = new Society();
        society.setId(1);
        task.setSociety(society);
        t2.setSociety(society);

        User u1 = new User("wen", "123");
        User u2 = new User("admin", "123");
        // 执行者
        List<User> executor = new ArrayList<>();
        executor.add(u1);
        executor.add(u2);
        task.setExecutors(executor);

        List<User> executor2 = new ArrayList<>();
        executor2.add(u2);
        t2.setExecutors(executor2);


        taskDao.add(task);
//        taskDao.add(t2);

        List<Task> tasks = taskDao.getTasksByUserId("wen");
        System.out.println(tasks);
    }

    @Test
    public void archive() throws Exception {
//        taskDao.archive(2);
    }
}