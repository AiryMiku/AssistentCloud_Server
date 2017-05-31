package com.kexie.acloud.service;

import com.kexie.acloud.config.AppConfig;
import com.kexie.acloud.domain.Room;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created : wen
 * DateTime : 2017/5/31 19:03
 * Description :
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class IMServiceTest {

    @Resource
    public IIMService mIMService;

    @Test
    public void getRoomInfo() throws Exception {
    }

    @Test
    public void createRoom() throws Exception {
    }

    @Test
    public void getRoomsByUserId() throws Exception {
        List<Room> rooms = mIMService.getRoomsByUserId("helloworld.wen@gmail.com");
        System.out.println(rooms);
    }

}