package com.ikoori.vip.mobile.controller;

import java.util.Map;

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
import com.ikoori.vip.common.constant.Const;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.util.WeChatAPI;

/**
 * 微信网页授权后回调地址
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController 
{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DubboConsumer consumer;
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public String index(HttpServletRequest request,HttpServletResponse response, Map<String, Object> map) throws Exception
    {
    	HttpSession session = request.getSession();
		// 判断是否为网页授权后返回的state,保证只能网页授权回调该登录方法
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			// 获得state
			String state = null;
			int stateIndex = queryString.indexOf("state");
			if (stateIndex != -1) {
				state = queryString.substring(stateIndex + 6, stateIndex+42);
			}
			Object user_state = session.getAttribute(Const.SESSION_USER_STATE);
			if (StringUtils.isBlank(state) || user_state == null || !state.equals(user_state.toString())) {
				log.error("state 信息不正确");
				throw new Exception("state 信息不正确");
			}
		}else{
			log.error("state 信息不正确");
			throw new Exception("state 信息不正确");
		}
		
    	
		String openid = request.getParameter("openid");
    	UserInfo userInfo = WeChatAPI.getUserInfo(session);
    	if(userInfo == null){
    		userInfo = WeChatAPI.getUserInfo(openid);
    	}
    	if(userInfo == null){
			log.error("没有找到用户");
    		throw new Exception("没有找到用户");
		}
    	
    	// 新用户生成会员信息
    	try {
			consumer.getMemberInfoApi().get().saveMemberInfo(userInfo);
		} catch (Exception e) {
			log.error("保存会员信息失败",e);
			throw new Exception("保存会员信息失败");
		}
    	
    	// 保存用户session
    	session.setAttribute(Const.SESSION_USER_INFO,userInfo);
    	
    	String lastAccessUrl = request.getParameter("lastAccessUrl");
    	// 返回初始访问页面，如果是/login ，避免出现循环访问，不处理
    	if(StringUtils.isNotBlank(lastAccessUrl) && !lastAccessUrl.contains("/login")){
    		return "redirect:" + lastAccessUrl;
    	}
    	return "redirect:index";
    }
    
    public static void main(String[] args) {
    	String queryString = "lastAccessUrl=http://localhost:8088/?null?state=b9b3fabb-a4b2-443f-bd16-9294b8fbdba4&openid=o19yZsw5CT7CDk_ikBRiGNbyu7Tw";
    	int stateIndex = queryString.indexOf("state");
    	String state = null;
		if (stateIndex != -1) {
			state = queryString.substring(stateIndex + 6, stateIndex+42);
		}
		System.out.println(state);
	}

}
