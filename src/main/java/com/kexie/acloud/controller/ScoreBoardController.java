package com.kexie.acloud.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kexie.acloud.dao.ISocietyDao;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * Created by zojian on 2017/6/7.
 */
@RequestMapping("/scoreboard")
@RestController
public class ScoreBoardController {

    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

    @Autowired
    ISocietyDao societyDao;

    /**
     * 获取社团周/月积分榜单
     * @param type
     * @param societyId
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{type}/{societyId}",method = RequestMethod.GET)
    public JSONArray getScoreBoard(@PathVariable("type") String type,
                                   @PathVariable("societyId") int societyId,
                                   @RequestAttribute("userId") String userId) throws Exception {
        Set<Tuple> tuples = null;
        if(societyDao.isInSociety(societyId,userId)) {
            if(type.equals("week")) {
                tuples = RedisUtil.getWeekScoreboard(jedisConnectionFactory.getJedis(), societyId);
            }
            else if(type.equals("month")){
                tuples = RedisUtil.getMonthScoreboard(jedisConnectionFactory.getJedis(),societyId);
            }
            JSONArray array = new JSONArray();
            tuples.forEach(tuple -> {
                JSONObject json = new JSONObject();
                json.put("userId", tuple.getElement());
                json.put("score", tuple.getScore());
                array.add(json);
            });
            return array;
        }
        else
            throw new Exception("该用户未在社团中");
    }


}
