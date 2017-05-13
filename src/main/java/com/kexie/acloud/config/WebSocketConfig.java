package com.kexie.acloud.config;

import com.kexie.acloud.interceptor.MeetingHandShake;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/5/11 23:09
 * Description :
 */
@Service
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Resource
    WebSocketHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 表示添加了一个/socket端点，客户端就可以通过这个端点来进行连接。
        registry.addHandler(handler, "/ws")
                .addInterceptors(new MeetingHandShake());

        registry.addHandler(handler, "/ws/sockjs")
                // 添加一个拦截器
                .addInterceptors(new MeetingHandShake())
                // 开启sockJs的支持 , 什么是sockJs
                .withSockJS();
    }
}
