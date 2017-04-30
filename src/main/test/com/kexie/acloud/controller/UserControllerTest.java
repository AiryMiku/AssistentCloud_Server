package com.kexie.acloud.controller;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.domain.User;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created : wen
 * DateTime : 2017/4/30 11:00
 * Description :
 */
public class UserControllerTest {
    @Test
    public void register() throws Exception {

        String json = "{\"userId\":\"wen1\",\"password\":\"05280122\",\"salt\":\"null\",\"hash\":\"null\",\"realName\":\"\",\"nickName\":\"\",\"stuId\":\"\",\"major\":1,\"classNum\":\"null\",\"phone\":0,\"gender\":0,\"logoUrl\":\"null\"}";

        User u = JSON.parseObject(json,User.class);
        System.out.println(u);
    }

}