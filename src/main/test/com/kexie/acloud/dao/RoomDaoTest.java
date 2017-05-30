package com.kexie.acloud.dao;

import com.kexie.acloud.config.AppConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created : wen
 * DateTime : 2017/5/30 13:44
 * Description :
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
// 添加事务处理懒加载问题
@Transactional
public class RoomDaoTest {

    @Resource
    IRoomDao mRoomDao;

    @Test
    public void getRoom() throws Exception {
        System.out.println(mRoomDao.getRoom(7));
    }

}