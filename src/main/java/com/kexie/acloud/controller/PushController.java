package com.kexie.acloud.controller;

import com.kexie.acloud.util.PushMessage;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 获取用户所有的未读通知
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{type}",method = RequestMethod.GET)
    public Set<PushMessage> getNoticeMsg(@RequestAttribute("userId") String userId,
                                         @PathVariable("type") String type){
        return RedisUtil.getMsg(jedisConnectionFactory.getJedis(),userId,type);
    }

    /**
     * 获取用户未读通知的数量
     * @param userId
     * @return
     */
    @RequestMapping(value = "/count/{type}",method = RequestMethod.GET)
    public Long getNoticeMsgCount(@RequestAttribute("userId") String userId,
                                  @PathVariable("type") String type){
        return RedisUtil.getMsgCount(jedisConnectionFactory.getJedis(),userId,type);
    }
}
