package com.kexie.acloud.controller;

import com.kexie.acloud.service.IIMService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * todo 查询房间号
     */

    /**
     * todo 查询用户拥有的房间号
     */
}
