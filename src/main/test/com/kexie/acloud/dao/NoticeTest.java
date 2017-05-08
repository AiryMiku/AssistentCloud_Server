package com.kexie.acloud.dao;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.domain.Notice;
import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zojian on 2017/5/8.
 */
public class NoticeTest extends com.kexie.acloud.dao.BaseTest {
    @Autowired
    INoticeDao noticeDao;

    @Autowired
    ISocietyDao societyDao;

    @Autowired
    IUserDao userDao;

    @Test
    public void addNotice(){
        Society society = societyDao.getSocietyById(1);
        User user = userDao.getUser("zojian@qq.com");
        Notice notice = new Notice();
        notice.setTitle("标题6");
        notice.setContent("内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
        notice.setPublisher(user);
        notice.setSociety(society);
        List<User> list = new ArrayList<>();
        list.add(userDao.getUser("admin@qq.com"));
        notice.setExecutors(list);
        System.out.println(JSON.toJSONString(notice));
        noticeDao.addNotice(notice);
    }
}
