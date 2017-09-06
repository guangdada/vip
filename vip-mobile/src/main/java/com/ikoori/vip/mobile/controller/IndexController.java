package com.ikoori.vip.mobile.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.util.WeChatAPI;

@Controller
@RequestMapping("/index")
public class IndexController {
	 @Autowired
	 DubboConsumer consumer;


	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String index(HttpServletRequest request, Map<String, Object> map) throws Exception {
		String openId = WeChatAPI.getOpenId(request.getSession());
		if(openId == null){
			throw new Exception("登录信息有误");
		}
		JSONObject obj = consumer.getMemberCardApi().get().getMemberCardByOpenId(openId);
		map.put("card", obj);
		return "/index.html";
	}
}
