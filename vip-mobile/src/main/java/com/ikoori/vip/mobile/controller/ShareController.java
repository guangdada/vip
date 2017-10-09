package com.ikoori.vip.mobile.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ikoori.vip.common.util.IpUtil;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.util.WeChatAPI;

@Controller
@RequestMapping("/share")
public class ShareController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DubboConsumer consumer;

	/**
	 * 跳转邀请函转发页面
	 * 
	 * @Title: invitation
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 * @date: 2017年10月16日 下午6:00:49
	 * @author: chengxg
	 */
	@RequestMapping(value = "/invitation", method = { RequestMethod.GET, RequestMethod.POST })
	public String invitation(HttpServletRequest request, Map<String, Object> map) throws Exception {
		return "/member_invitation.html";
	}

	/**
	 * 接收邀请请求
	 * 
	 * @Title: signIn
	 * @param session
	 * @return
	 * @date: 2017年10月16日 下午5:44:01
	 * @author: chengxg
	 * @throws Exception
	 */
	@RequestMapping("/invited/{shareOpenid}")
	public String invited(@PathVariable String shareOpenid, HttpSession session, HttpServletRequest request)
			throws Exception {
		String openId = WeChatAPI.getOpenId(session);
		if (openId == null) {
			throw new Exception("登录信息有误");
		}
		consumer.getShareApi().get().saveShareLog(shareOpenid, openId, IpUtil.getIpAddr(request));
		return "";
	}
}
