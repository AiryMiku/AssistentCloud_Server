package com.kexie.acloud.controller;

import com.kexie.acloud.util.PushMessage;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by zojian on 2017/6/2.
 */
@RestController()
@RequestMapping(value = "/message", produces = {"application/json;charset=UTF-8"})
public class PushController {
    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

    /**
     * 获取用户所有的公告未读通知
     * @param userId
     * @return
     */
    @RequestMapping(value = "/notice",method = RequestMethod.GET)
    public Set<PushMessage> getNoticeMsg(@RequestAttribute("userId") String userId){
        return RedisUtil.getMsg(jedisConnectionFactory.getJedis(),userId,"notice");
    }

    /**
     * 获取用户未读公告消息的数量
     * @param userId
     * @return
     */
    @RequestMapping(value = "/count/notice",method = RequestMethod.GET)
    public Long getNoticeMsgCount(@RequestAttribute("userId") String userId){
        return RedisUtil.getMsgCount(jedisConnectionFactory.getJedis(),userId,"notice");
    }
}
