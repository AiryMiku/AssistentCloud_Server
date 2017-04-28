package com.kexie.acloud.controller;

import com.kexie.acloud.domain.ErrorBody;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.service.IUserService;
import com.kexie.acloud.util.EncryptionUtil;
import com.kexie.acloud.util.UserUtil;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created : wen
 * DateTime : 2017/4/8 13:28
 * Description :
 */
@Controller
@CrossOrigin("*")
public class UserController {

    @Resource(name = "UserService")
    private IUserService mUserService;

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

    @ResponseBody
    @RequestMapping(value = "/login/web", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public User webLogin(@Valid User user,
                         BindingResult result,
                         HttpSession session
    ) throws UserException {

        // todo 是不是该弄个拦截器什么的
//        TestUser u = (TestUser) session.getAttribute("user");
//        if (u != null) return u;

        // 验证表单
        checkForm(result);

        User loginUser = mUserService.login(user);
        // 获取应该返回的字段
        loginUser = UserUtil.getCilentUserField(loginUser);

        session.setAttribute("user", loginUser);

        return loginUser;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public User register(@Valid User user,
                         BindingResult result,
                         HttpServletResponse response,
                         HttpSession session) throws UserException {

        checkForm(result);

        User registerUser = mUserService.register(user);

        String newToken = EncryptionUtil.generateMD5(registerUser.getUserId());
        response.addCookie(new Cookie("token", newToken));


        // 获取要返回的字段
        User clientUser = UserUtil.getCilentUserField(registerUser);

        session.setAttribute("user",clientUser);
        // 添加到redis
//        RedisUtil.set(newToken, registerUser.getUserId());

        return clientUser;

    }

    @ResponseBody
    @RequestMapping(value = "/user/info", method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    public String getUserInfo(HttpServletResponse response,
                            @CookieValue(value = "token", required = false) String token) throws UserException {

        // 验证token
//        if (token != null) {
//            String userId = RedisUtil.get(token);
//            if (userId != null) {
//                TestUser userByUserId = mUserService.getUserByUserId(userId);
//                return UserUtil.getCilentUserField(userByUserId);
//            }
//        }

//        throw new UserException("用户没有登录");
        return "不写先";
    }

    /**
     * 处理用户异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(UserException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorBody handlerException(UserException e) {
        return new ErrorBody(e.getMessage());
    }

    /**
     * 处理表单的错误信息，在登录注册中，只需要返回第一条错误信息就可以了
     *
     * @param result
     * @throws UserException
     */
    private void checkForm(BindingResult result) throws UserException {
        if (result.hasErrors()) {
            throw new UserException(result.getFieldErrors().get(0).getDefaultMessage());
        }
    }
}
