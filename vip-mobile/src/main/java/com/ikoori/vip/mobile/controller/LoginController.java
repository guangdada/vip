package com.ikoori.vip.mobile.controller;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.util.WeChatAPI;

/**
 * 微信网页授权后回调地址
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DubboConsumer consumer;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map)
			throws Exception {
		log.info("进入登录方法");
		HttpSession session = request.getSession();
		// 判断是否为网页授权后返回的state,保证只能网页授权回调该登录方法
		String queryString = request.getQueryString();
		log.info("queryString:" +queryString);
		if (StringUtils.isNotBlank(queryString)) {
			Pattern pattern = Pattern.compile("state=[-\\w]{36}");
			Matcher matcher = pattern.matcher(queryString);
			// 截取36位state
			String state = matcher.find() ? matcher.group(0) : null;
			log.info("state:" +state);
			Object session_state = session.getAttribute(WeChatAPI.SESSION_USER_STATE);
			if (StringUtils.isBlank(state) || session_state == null || !state.contains(session_state.toString())) {
				log.error("state 信息不正确");
				throw new Exception("state 信息不正确");
			}
		} else {
			log.error("state 信息不正确");
			throw new Exception("state 信息不正确");
		}

		String openid = request.getParameter("openid");
		log.info("openid:" +openid);
		UserInfo userInfo = WeChatAPI.getUserInfo(session);
		if (userInfo == null) {
			userInfo = WeChatAPI.getUserInfo(openid);
		}
		if (userInfo == null) {
			log.error("没有找到用户");
			throw new Exception("没有找到用户");
		}

		// 新用户生成会员信息
		try {
			consumer.getMemberInfoApi().get().saveMemberInfo(userInfo);
		} catch (Exception e) {
			log.error("保存会员信息失败", e);
			throw new Exception("保存会员信息失败");
		}

		// 保存用户session
		session.setAttribute(WeChatAPI.SESSION_USER_INFO, userInfo);

		String lastAccessUrl = request.getParameter("lastAccessUrl");
		// 返回初始访问页面，action参数采用rest方式配置，不要使用?号
		if (StringUtils.isNotBlank(lastAccessUrl)) {
			log.info("返回初始访问页面", lastAccessUrl);
			return "redirect:" + lastAccessUrl;
		}
		log.info("跳转首页", "redirect:/index");
		return "redirect:/index";
	}
	
	public static void main(String[] args) {
		String queryString = "http://localhost:8088/login/state=" + UUID.randomUUID() + "addd";
		System.out.println(queryString);
		Pattern pattern = Pattern.compile("state=[-\\w]{36}");
		Matcher matcher = pattern.matcher(queryString);
		// 截取36位state
		String state = matcher.find() ? matcher.group(0) : null;
		System.out.println(state);
	}
}
