package com.kexie.acloud.controller;

import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.AuthorizedException;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.service.IUserService;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/8 13:28
 * Description :
 */
@RestController
@RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
public class UserController {

    @Resource(name = "UserService")
    private IUserService mUserService;

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public User getUserInfo(@RequestAttribute("userId") String userId) throws UserException {
        User user = mUserService.getUserByUserId(userId);
        User result = new User();
        BeanUtils.copyProperties(user, result, User.CLIENT_IGNORE_FIELD);
        return result;
    }

    /**
     * 更新用户信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public User updateUser(@Validated User user, BindingResult form,
                           @RequestAttribute("userId") String userId) throws FormException, AuthorizedException {

        if (form.hasErrors()) {
            throw new FormException(form);
        }
        user.setUserId(userId);

        User update = mUserService.update(user);
        User result = new User();
        BeanUtils.copyProperties(update, result, User.CLIENT_IGNORE_FIELD);
        return result;
    }

}
