package com.kexie.acloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zojian on 2017/4/26.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    StringRedisTemplate redisTemplate;

    //管理员后台界面
    @RequestMapping(method = RequestMethod.GET
            ,produces = {"application/json;charset=UTF-8"})
    public String index(){
       return "admin-index";
    }



    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test(){
        String key = "spring-test";
        redisTemplate.boundListOps(key).leftPush("aha");
        System.out.println(redisTemplate.boundListOps(key).leftPop());
    }

}
