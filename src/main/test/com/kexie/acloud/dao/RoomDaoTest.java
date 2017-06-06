package com.kexie.acloud.dao;

import com.kexie.acloud.config.AppConfig;
import com.kexie.acloud.domain.Room;
import com.kexie.acloud.domain.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

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
@Rollback(false)
public class RoomDaoTest {
    @Test
    public void addRoom() throws Exception {
        Room room = new Room();
        List<User> members = new ArrayList<>();
        members.add(new User("helloworld.wen@gmail.com"));
        room.setMember(members);
        room.setName("测试");
        mRoomDao.addRoom(room);
    }

    @Resource
    IRoomDao mRoomDao;

    @Test
    public void getRoom() throws Exception {
        System.out.println(mRoomDao.getRoom(7));
    }

}