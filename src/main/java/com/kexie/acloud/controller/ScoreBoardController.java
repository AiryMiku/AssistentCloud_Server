package com.kexie.acloud.controller;

import com.kexie.acloud.dao.ISocietyDao;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Set<String> getScoreBoard(@PathVariable("type") String type,
                             @PathVariable("societyId") int societyId,
                             @RequestAttribute("userId") String userId) throws Exception {
        if(societyDao.isInSociety(societyId,userId)) {
            if(type.equals("week")) {
                return RedisUtil.getWeekScoreboard(jedisConnectionFactory.getJedis(), societyId);
            }
            else if(type.equals("month")){
                return RedisUtil.getMonthScoreboard(jedisConnectionFactory.getJedis(),societyId);
            }
        }
        else
            throw new Exception("该用户未在社团中");
        return null;
    }


}
