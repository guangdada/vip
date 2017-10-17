package com.ikoori.vip.mobile.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.util.WeChatAPI;

@Controller
@RequestMapping("/sign")
public class SignController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DubboConsumer consumer;

	@RequestMapping(value = "/toSign", method = { RequestMethod.GET, RequestMethod.POST })
	public String sign(HttpServletRequest request, Map<String, Object> map, Long storeId) throws Exception {
		log.info("进入sign");
		//获取微信信息
		UserInfo userInfo = WeChatAPI.getUserInfo(request.getSession());
		if (userInfo == null) {
			throw new Exception("登录信息有误");
		}
		
		// 获取微信头像和昵称
		//String openId = WeChatAPI.getOpenId(request.getSession());
		//Object userInfo = consumer.getMemberInfoApi().get().getWxUserByOpenId(openId);
		map.put("userInfo", userInfo);
		return "/member_sign.html";
	}

	@RequestMapping("/signIn")
	@ResponseBody
	public Object signIn(HttpSession session) {
		log.info("进入signIn");
		String openId = WeChatAPI.getOpenId(session);
		JSONObject obj = new JSONObject();
		try {
			obj = consumer.getSignApi().get().signIn(openId);
		} catch (Exception e) {
			log.error("领取优惠券失败", e);
			obj.put("code", 500);
			// 判断是否为业务异常
			if (StringUtils.isNotBlank(e.getMessage()) && e.getMessage().matches("\\{(.*)\\}")) {
				obj.put("msg", JSONObject.parseObject(e.getMessage()).get("msg"));
			} else {
				obj.put("msg", "发生未知错误!");
			}
		}
		log.info("结束signIn");
		return obj;
	}

	@RequestMapping("/signInfo")
	@ResponseBody
	public Object signInfo(HttpSession session) throws Exception {
		log.info("进入signInfo");
		String openId = WeChatAPI.getOpenId(session);
		JSONObject obj = consumer.getSignApi().get().getSignInfo(openId);
		log.info("结束signInfo");
		return obj;
	}
}
