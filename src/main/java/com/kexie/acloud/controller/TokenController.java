package com.kexie.acloud.controller;

import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.service.IUserService;
import com.kexie.acloud.util.TokenManager;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
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
    private IUserService mUserService;

    @RequestMapping("/login")
    public void login(@Validated(User.LoginForm.class) User user, BindingResult form,
                      HttpServletResponse response) throws FormException, UserException {

        // 表单验证
        if (form.hasErrors()) throw new FormException(form);

        // 登录验证
        mUserService.login(user);

        // 登录成功，生成一个token
        String token = TokenManager.createToken(user.getUserId());

        // 返回给客户端
        response.addCookie(new Cookie("token", token));
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@Validated(User.RegisterForm.class) User user,
                         BindingResult result,
                         HttpServletResponse response) throws UserException, FormException {

        if (result.hasErrors()) throw new FormException(result);

        mUserService.register(user);

        String token = TokenManager.createToken(user.getUserId());

        // 返回给客户端
        response.addCookie(new Cookie("token", token));
    }

}
