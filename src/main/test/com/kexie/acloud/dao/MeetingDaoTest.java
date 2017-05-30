package com.kexie.acloud.dao;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.config.AppConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created : wen
 * DateTime : 2017/5/30 0:41
 * Description :
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
// 添加事务处理懒加载问题
@Transactional
public class MeetingDaoTest extends com.kexie.acloud.dao.BaseTest {

    @Resource
    IMeetingDao mMeetingDao;

    @Test
    public void getCurrentUserNotStartMeeting() throws Exception {
        System.out.println(JSON.toJSONString( mMeetingDao.getCurrentUserNotStartMeeting("helloworld.wen@gmail.com").get(0).getPublisher()));
    }

}