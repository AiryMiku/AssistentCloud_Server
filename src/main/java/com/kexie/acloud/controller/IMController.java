package com.kexie.acloud.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kexie.acloud.domain.Room;
import com.kexie.acloud.service.IIMService;

import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/5/15 10:13
 * Description :
 */
@RestController
@RequestMapping(value = "chat")
public class IMController {

    @Resource
    private IIMService mIMService;

    /**
     * 获取当前登录用户拥有的房间
     *
     * @param userId 登录用户的Id
     * @return 所有房间
     */
    @RequestMapping(method = RequestMethod.GET)
    public JSONArray getRooms(@RequestAttribute("userId") String userId) {
        List<Room> rooms = mIMService.getRoomsByUserId(userId);

        JSONArray result = new JSONArray();
        rooms.forEach(room -> {
            JSONObject object = new JSONObject();
            JSONObject master = new JSONObject();

            master.put("userId", room.getMaster().getUserId());
            master.put("nickName", room.getMaster().getNickName());
            master.put("logo", room.getMaster().getLogoUrl());

            object.put("roomId", room.getRoomId());
            object.put("name", room.getName());
            object.put("master", master);
            object.put("type", room.getRoomType());

            result.add(object);
        });

        return result;
    }
}
