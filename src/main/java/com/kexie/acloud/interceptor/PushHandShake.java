package com.kexie.acloud.interceptor;

import com.kexie.acloud.exception.AuthorizedException;
import com.kexie.acloud.util.TokenManager;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zojian on 2017/6/5.
 */
public class PushHandShake implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            HttpServletRequest req = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();

            Cookie[] cookies = req.getCookies();

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

            map.put("userId", userId);

        }
        return true;

    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
