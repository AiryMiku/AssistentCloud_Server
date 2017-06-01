package com.kexie.acloud.interceptor;

import com.kexie.acloud.exception.AuthorizedException;
import com.kexie.acloud.exception.RoomException;
import com.kexie.acloud.util.TokenManager;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created : wen
 * DateTime : 2017/5/11 23:15
 * Description :
 */
//如果采用WebSocketConfig类配置，需加上这一行；采用XML配置则不用e
public class MeetingHandShake implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();

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

            String roomId = req.getParameter("roomId");

            if (roomId == null || roomId.equals(""))
                throw new RoomException("没用RoomId这个参数哦");

            String userId = TokenManager.getTokenValue(token);

            // 设置WebSocket的参数
            attributes.put("userId", userId);
            attributes.put("roomId", req.getParameter("roomId"));

        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("握手之后");
    }
}
