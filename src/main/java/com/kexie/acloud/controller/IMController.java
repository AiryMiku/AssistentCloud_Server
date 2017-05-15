package com.kexie.acloud.controller;

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
     * todo 查询用户拥有的房间号
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Room> getRooms(@RequestAttribute("userId") String userId) {
        return mIMService.getRoomsByUserId(userId);
    }
}
