package com.kexie.acloud.interceptor;

import com.kexie.acloud.exception.AuthorizedException;
import com.kexie.acloud.util.TokenManager;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created : wen
 * DateTime : 2017/5/7 23:21
 * Description : 拦截没有登录请求,没有带token的请求
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();

        if (cookies == null)
            throw new AuthorizedException("用户未登录,cookies为空");

        Cookie tokenCookie = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                tokenCookie = cookie;
                break;
            }
        }

        if (tokenCookie == null)
            throw new AuthorizedException("用户未登录，没有token的cookie");

        String token = tokenCookie.getValue();

        if (!TokenManager.checkToken(token))
            throw new AuthorizedException("用户未登录，token验证失败");

        String userId = TokenManager.getTokenValue(token);

        request.setAttribute("userId", userId);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
