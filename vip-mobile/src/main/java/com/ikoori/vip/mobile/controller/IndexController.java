package com.ikoori.vip.mobile.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.util.WeChatAPI;

@Controller
@RequestMapping("/index")
public class IndexController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	 @Autowired
	 DubboConsumer consumer;


	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String index(HttpServletRequest request, Map<String, Object> map) throws Exception {
		log.info("进入index");
		String unionid = WeChatAPI.getUnionid(request.getSession());
		if(unionid == null){
			throw new Exception("登录信息有误");
		}
		JSONObject obj = consumer.getMemberCardApi().get().getMemberCardByUnionid(unionid);
		map.put("card", obj);
		log.info("结束index");
		return "/index.html";
	}
}
