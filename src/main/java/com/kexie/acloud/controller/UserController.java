package com.kexie.acloud.controller;

import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.AuthorizedException;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.service.IUserService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    /**
     * 上传社团logo
     *
     * @param logo
     * @param societyId
     */
    @RequestMapping(value = "logo", method = RequestMethod.POST)
    public void uploadLogo(HttpServletRequest request, @RequestParam("logo") MultipartFile logo,
                           @RequestAttribute("userId") String userId) throws IOException {

        String realName = logo.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + realName.substring(realName.lastIndexOf("."));

        String systemPath = request.getSession().getServletContext()
                .getRealPath(File.separator + "resources" + File.separator + "user" + File.separator + "logo");
        // 写入本地中
        FileUtils.copyInputStreamToFile(logo.getInputStream(), new File(systemPath, fileName));

        String relativePath = "/resources/user/logo/" + fileName;
        mUserService.updateUserLogo(userId, relativePath);
    }

}
