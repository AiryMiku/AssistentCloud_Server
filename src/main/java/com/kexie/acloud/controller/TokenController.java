package com.kexie.acloud.controller;

import com.kexie.acloud.dao.IUserDao;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.service.IUserService;
import com.kexie.acloud.util.TokenManager;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created : wen
 * DateTime : 2017/5/6 21:34
 * Description : 在这里做登录注册的操作，RESTful是对数据进行增删改查，那么登录注册就是对token的增删改查
 */
@RestController
@RequestMapping(produces = {"application/json;charset=UTF-8"})
public class TokenController {

    @Resource
    IUserService mUserService;

    @RequestMapping("login")
    public void login(@Validated(User.LoginForm.class) User user, BindingResult form,
                      HttpServletResponse response,
                      HttpServletRequest request) throws FormException, UserException {

        if (form.hasErrors()) throw new FormException(form);

        mUserService.login(user);

        // 登录成功，生成一个token
        String token = TokenManager.createToken(user.getUserId());

        // 如果使用
        // TODO: 2017/5/6 token的处理
    }
}
