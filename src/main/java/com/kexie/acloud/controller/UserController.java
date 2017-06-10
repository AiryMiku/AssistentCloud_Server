package com.kexie.acloud.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.List;
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
    public JSONObject getUserInfo(@RequestAttribute("userId") String userId) throws UserException {
        User user = mUserService.getUserByUserId(userId);

        JSONObject json = new JSONObject(true);

        JSONObject major = new JSONObject(true);
        JSONObject college = new JSONObject(true);
        JSONObject school = new JSONObject(true);

        if (user.getMajor() != null) {
            school.put("id", user.getMajor().getCollege().getSchool().getId());
            school.put("name", user.getMajor().getCollege().getSchool().getName());

            college.put("id", user.getMajor().getCollege().getId());
            college.put("name", user.getMajor().getCollege().getName());
            college.put("school", school);

            major.put("id", user.getMajor().getId());
            major.put("name", user.getMajor().getName());
            major.put("college", college);
        }
        json.put("gender", user.getGender());
        json.put("logoUrl", user.getLogoUrl());
        json.put("major", major);
        json.put("nickName", user.getNickName());
        json.put("phone", user.getPhone());
        json.put("realName", user.getNickName());
        json.put("societyPositions", user.getSocietyPositions());
        json.put("stuId", user.getStuId());
        json.put("userId", user.getUserId());

        return json;
    }

    /**
     * 更新用户信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject updateUser(@Validated User user, BindingResult form,
                                 @RequestAttribute("userId") String userId) throws FormException, AuthorizedException {

        if (form.hasErrors()) {
            throw new FormException(form);
        }
        user.setUserId(userId);

        User update = mUserService.update(user);

        JSONObject json = new JSONObject(true);

        JSONObject major = new JSONObject(true);
        JSONObject college = new JSONObject(true);
        JSONObject school = new JSONObject(true);

        if (update.getMajor() != null) {
            school.put("id", update.getMajor().getCollege().getSchool().getId());
            school.put("name", update.getMajor().getCollege().getSchool().getName());

            college.put("id", update.getMajor().getCollege().getId());
            college.put("name", update.getMajor().getCollege().getName());
            college.put("school", school);

            major.put("id", update.getMajor().getId());
            major.put("name", update.getMajor().getName());
            major.put("college", college);
        }

        json.put("gender", update.getGender());
        json.put("logoUrl", update.getLogoUrl());
        json.put("major", major);
        json.put("nickName", update.getNickName());
        json.put("phone", update.getPhone());
        json.put("realName", update.getNickName());
        json.put("societyPositions", update.getSocietyPositions());
        json.put("stuId", update.getStuId());
        json.put("userId", update.getUserId());

        return json;
    }

    /**
     * 上传社团logo
     *
     * @param logo
     * @param userId
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

    /**
     * 通过id或者昵称模糊搜索用户
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public JSONArray searchUser(@RequestParam("query") String query) {

        if (query.equals("")) return null;

        List<User> users = mUserService.searchUser(query);
        JSONArray array = new JSONArray();

        users.forEach(user -> {
            JSONObject json = new JSONObject();

            json.put("nickName", user.getNickName());
            json.put("logo", user.getLogoUrl());
            json.put("userId", user.getUserId());

            array.add(json);
        });

        return array;
    }
}
