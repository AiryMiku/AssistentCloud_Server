package com.kexie.acloud.controller;


import com.alibaba.fastjson.JSON;
import com.kexie.acloud.entity.Message;
import com.kexie.acloud.entity.TestUser;
import com.kexie.acloud.websocket.MyWebSocketHandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/msg")
public class MsgController {

//	@Resource
	MyWebSocketHandler handler;

	Map<Long, TestUser> users = new HashMap<Long, TestUser>();

	// 模拟一些数据
	@ModelAttribute
	public void setReqAndRes() {
		TestUser u1 = new TestUser();
		u1.setId(1L);
		u1.setName("张三");
		users.put(u1.getId(), u1);

		TestUser u2 = new TestUser();
		u2.setId(2L);
		u2.setName("李四");
		users.put(u2.getId(), u2);

	}

	// 用户登录
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView doLogin(TestUser testUser, HttpServletRequest request) {
		request.getSession().setAttribute("uid", testUser.getId());
		request.getSession().setAttribute("name", users.get(testUser.getId()).getName());
		return new ModelAndView("redirect:talk");
	}

	// 跳转到交谈聊天页面
	@RequestMapping(value = "talk", method = RequestMethod.GET)
	public ModelAndView talk() {
		return new ModelAndView("talk");
	}

	// 跳转到发布广播页面
	@RequestMapping(value = "broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
		return new ModelAndView("broadcast");
	}

	// 发布系统广播（群发）
	@ResponseBody
	@RequestMapping(value = "broadcast", method = RequestMethod.POST)
	public void broadcast(String text) throws IOException {
		Message msg = new Message();
		msg.setDate(new Date());
		msg.setFrom(-1L);
		msg.setFromName("系统广播");
		msg.setTo(0L);
		msg.setText(text);
		handler.broadcast(new TextMessage(JSON.toJSONString(msg)));
	}

}