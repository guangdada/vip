package com.ikoori.vip.mobile.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ikoori.vip.common.util.IpUtil;
import com.ikoori.vip.mobile.util.WeChatAPI;

public class WechatLogin implements HandlerInterceptor {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("进入WechatLogin>>IP = " + IpUtil.getIpAddr(request));
		// 从session中获取登录者实体
		Object obj = request.getSession().getAttribute(WeChatAPI.SESSION_USER_INFO);
		if (null == obj) {
			// 如果session中不存在登录者实体，则弹出框提示重新登录
			// 设置request和response的字符集，防止乱码
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			// 网页授权后回调的地址
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/login";
			log.info("basePath:" +basePath);
			
			// 用户最后访问的地址
			String state = UUID.randomUUID().toString();
			request.getSession().setAttribute("state", state);
			log.info("state:" +state);
			
			//String lastAccessUrl = request.getRequestURL() + "?" + request.getQueryString();
			String lastAccessUrl = request.getRequestURL().toString();
			String redirect_uri = basePath + "?lastAccessUrl=" + lastAccessUrl;
			
			StringBuffer sb = new StringBuffer(WeChatAPI.weboauth);
			sb.append("?").append("scope=snsapi_base").append("&state=").append(state).append("&redirect_uri=").append(redirect_uri);
			log.info("redirect:" +sb.toString());
			response.sendRedirect(sb.toString());
		} else {
			// 如果session中存在登录者实体，则继续
			log.info("已经登录过");
			return true;
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
