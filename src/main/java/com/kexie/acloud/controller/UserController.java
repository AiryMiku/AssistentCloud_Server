package com.kexie.acloud.controller;

import com.kexie.acloud.domain.ErrorBody;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.service.IUserService;
import com.kexie.acloud.util.UserUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created : wen
 * DateTime : 2017/4/8 13:28
 * Description :
 */
@Controller
@RestController
public class UserController {

    @Resource(name = "UserService")
    private IUserService mUserService;

    /**
     * App登录
     */
    @ResponseBody
    @RequestMapping(value = "/login/app", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public String appLogin(@Valid User user,
                           BindingResult result,
                           HttpServletResponse response,
                           @CookieValue(value = "token", required = false) String token) throws UserException {

        return "滚 ！！,都没写，你为什么要用";


        // 验证token
//        if (token != null) {
//            String userId = RedisUtil.get(token);
//            if (userId != null) {
//                TestUser userByUserId = mUserService.getUserByUserId(userId);
//                return UserUtil.getCilentUserField(userByUserId);
//            }
//        }
//
//         验证表单
//        checkForm(result);
//
//        TestUser loginUser = mUserService.login(user);
//
//        String newToken = EncryptionUtil.generateMD5(loginUser.getUserId());
//        response.addCookie(new Cookie("token", newToken));
//
//        // 添加到redis
//        RedisUtil.set(newToken, loginUser.getUserId());
//
//        return UserUtil.getCilentUserField(loginUser);
    }

    /**
     * 网页登录
     */
    @ResponseBody
    @RequestMapping(value = "/login/web", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public User webLogin(@Validated(value = User.LoginForm.class) User user,
                         BindingResult result,
                         HttpSession session
    ) throws UserException, FormException {

        User u = (User) session.getAttribute("user");

        if (u != null) {
            User r = new User();
            BeanUtils.copyProperties(u, r, User.CLIENT_IGNORE_FIELD);
            return r;
        }

        // 验证表单
        if (result.hasErrors()) {
            throw new FormException(result);
        }

        User loginUser = mUserService.login(user);

        session.setAttribute("user", loginUser);

        return UserUtil.getCilentUserField(loginUser);
    }

    /**
     * 注册
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public User register(@Validated(User.RegisterForm.class) User user,
                         BindingResult result,
                         HttpSession session) throws UserException, FormException {

        if (result.hasErrors()) {
            throw new FormException(result);
        }

        User registerUser = mUserService.register(user);

        // 获取要返回的字段
        User clientUser = UserUtil.getCilentUserField(registerUser);

        session.setAttribute("user", registerUser);

        return clientUser;

    }

    /**
     * 获取用户信息
     */
    @ResponseBody
    @RequestMapping(value = "/user/info", method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    public String getUserInfo(HttpServletResponse response,
                              @CookieValue(value = "token", required = false) String token) throws UserException {

        return "不写先";
    }

    /**
     * 处理数据库用户异常
     */
    @ExceptionHandler(UserException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorBody handlerException(UserException e) {
        e.printStackTrace();
        return new ErrorBody(e.getMessage());
    }

    /**
     * 表单异常
     */
    @ExceptionHandler(FormException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerFormException(FormException fe) {
        fe.printStackTrace();
        return fe.getErrorForm();
    }

}
