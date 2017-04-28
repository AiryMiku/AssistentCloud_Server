package com.kexie.acloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zojian on 2017/4/26.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    //管理员后台界面
    @RequestMapping(method = RequestMethod.GET
            ,produces = {"application/json;charset=UTF-8"})
    public String index(){
       return "admin-index";
    }



}
