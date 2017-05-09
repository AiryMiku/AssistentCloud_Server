package com.kexie.acloud.controller;

import com.kexie.acloud.domain.User;

import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/5/9 23:36
 * Description :
 */
@RestController
@RequestMapping(value = "/society", produces = {"application/json;charset=UTF-8"})
public class SocietyController {

    /**
     * 获取当前社团的所有用户
     */
    @RequestMapping("user")
    public List<User> getUser(@RequestAttribute("societyId") String societyId) {

    }
}
