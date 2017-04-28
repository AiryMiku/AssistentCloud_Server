package com.kexie.acloud.websocket;


import com.alibaba.fastjson.JSON;
import com.kexie.acloud.log.Log;

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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/msg")
public class MsgController {

	@Resource
	MyWebSocketHandler handler;

	Map<Long, TestUser> users = new HashMap<Long, TestUser>();

	// 模拟一些数据
	@ModelAttribute
	public void setReqAndRes() {

		Log.debug("初始化用户数据");

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

		Log.debug("Spring 控制器 doLogin");

		request.getSession().setAttribute("uid", testUser.getId());
		request.getSession().setAttribute("name", users.get(testUser.getId()).getName());
		return new ModelAndView("redirect:talk");
	}

	// 跳转到交谈聊天页面
	@RequestMapping(value = "talk", method = RequestMethod.GET)
	public ModelAndView talk() {
		Log.debug("跳转到聊天界面");
		return new ModelAndView("talk");
	}

	// 跳转到发布广播页面
	@RequestMapping(value = "broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
	    Log.debug("跳转到发广播界面");
		return new ModelAndView("broadcast");
	}

	// 发布系统广播（群发）
	@ResponseBody
	@RequestMapping(value = "broadcast", method = RequestMethod.POST)
	public void broadcast(String text) throws IOException {

		Log.debug("群发消息 :" + text);

		Message msg = new Message();
		msg.setDate(new Date());
		msg.setFrom(-1L);
		msg.setFromName("系统广播");
		msg.setTo(0L);
		msg.setText(text);
		handler.broadcast(new TextMessage(JSON.toJSONString(msg)));
	}

}