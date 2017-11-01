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

import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.util.IpUtil;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.config.properties.GunsProperties;
import com.ikoori.vip.mobile.util.WeChatAPI;

@Controller
@RequestMapping("/share")
public class ShareController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DubboConsumer consumer;
	@Autowired
	GunsProperties gunsProperties;

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
		log.info("进入invitation");
		UserInfo userInfo = WeChatAPI.getUserInfo(request.getSession());
		if (userInfo == null) {
			throw new Exception("登录信息有误");
		}

		// 获取微信头像和昵称
		//Object user = consumer.getMemberInfoApi().get().getWxUserByOpenId(openId);
		//map.put("user", user);
		map.put("userInfo", userInfo);
		//String shareUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				//+ "/share/invited/" + userInfo.getOpenid();
		String shareUrl = gunsProperties.getClientUrl() +  "/share/invited/" + userInfo.getOpenid();
		map.put("shareUrl", shareUrl);
		log.info("结束invitation");
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
		log.info("进入invited");
		String openId = WeChatAPI.getOpenId(session);
		if (openId == null) {
			throw new Exception("登录信息有误");
		}
		if (!shareOpenid.equals(openId)) {
			log.info("进入invited");
			consumer.getShareApi().get().saveShareLog(shareOpenid, openId, IpUtil.getIpAddr(request));
		}
		log.info("结束invited");
		return "redirect:/index";
	}
}
