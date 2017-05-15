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

    @Test
    public void getNoticesByPublisherId(){
        System.out.println(JSON.toJSONString(noticeDao.getNoticesByPublisherId("zojian@qq.com",2,10)));
    }

    @Test
    public void getNoticesByUserId(){
        System.out.println(JSON.toJSONString(noticeDao.getNoticesByUserId("admin@qq.com",1,100)));
    }

    @Test
    public void getNoticesByUserIdAndSocietyId(){
        System.out.println(JSON.toJSONString(noticeDao.getNoticesByUserIdAndSocietyId("zojian@qq.com",2,1,10)));
    }

    @Test
    public void getNoticeByNoticeId(){
        System.out.println(JSON.toJSONString(noticeDao.getNoticeByNoticeId(4)));
    }

    @Test
    public void deleteNotice(){
//        noticeDao.deleteNotice(32);
        getNoticeByNoticeId();
    }

    @Test
    public void updateNotice(){
        String json = "{\n" +
                "    \"title\": \"标题77\",\n" +
                "    \"content\": \"内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容\",\n" +
                "    \"society\": 1,\n" +
                "    \"publisher\": \"zojian@qq.com\",\n" +
                "    \"executors\": [\n" +
                "      \"admin123@qq.com\"\n" +
                "    ]\n" +
                " }";
        Notice notice = JSON.parseObject(json,Notice.class);
        System.out.println(notice);
//       assert noticeDao.updateNotice(35,notice)==true;
    }

}
