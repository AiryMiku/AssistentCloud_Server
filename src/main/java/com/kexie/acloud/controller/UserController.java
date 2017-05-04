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
@RequestMapping(produces = {"application/json;charset=UTF-8"})
public class UserController {

    public static final String SESSION_USER_KEY = "user";

    @Resource(name = "UserService")
    private IUserService mUserService;

    /**
     * App登录
     */
    @ResponseBody
    @RequestMapping(value = "/login/app", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public String appLogin() throws UserException {
        return "Todo";
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

        session.setAttribute(SESSION_USER_KEY, registerUser);

        return clientUser;

    }

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "/user/info", method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    public User getUserInfo(HttpSession session,
                            @CookieValue(value = "token", required = false) String token) throws UserException {

        User user = (User) session.getAttribute(SESSION_USER_KEY);
        User result = new User();

        if (user == null)
            throw new UserException("用户未登录");

        BeanUtils.copyProperties(user, result, User.CLIENT_IGNORE_FIELD);

        return result;
    }

    /**
     * 更新用户信息
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public User updateUser(@Validated User user, BindingResult form, HttpSession session) throws FormException {

        if (form.hasErrors()) {
            throw new FormException(form);
        }

        User update = mUserService.update(user);

        session.setAttribute(SESSION_USER_KEY, update);

        User result = new User();

        BeanUtils.copyProperties(update, result, User.CLIENT_IGNORE_FIELD);

        return result;
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
