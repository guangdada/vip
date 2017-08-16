package com.ikoori.vip.mobile.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ikoori.vip.common.constant.Const;

public class WechatInterceptor implements HandlerInterceptor{
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 不过滤的uri, 这里可以改善为ioc注入的方式
        String[] ignores = new String[] { "login", "code" };
        // 请求的uri
        String uri = request.getRequestURI();
        String params = "";
        if(StringUtils.isNotBlank(request.getQueryString())){
        	params  = "?" + request.getQueryString();
        }
        // 是否过滤
        boolean doFilter = true;

        for (String ignore : ignores)
        {
            if (uri.indexOf(ignore) != -1)
            {
                // 如果uri中包含不过滤的uri，则不进行过滤
                doFilter = false;
                break;
            }
        }
        if (doFilter)
        {
            // 执行过滤
            // 从session中获取登录者实体
            Object obj = request.getSession().getAttribute(Const.SESSION_USER_INFO);
            if (null == obj)
            {
                // 如果session中不存在登录者实体，则弹出框提示重新登录
                // 设置request和response的字符集，防止乱码
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                request.setAttribute("url", uri + params);
                request.getRequestDispatcher("/login").forward(request, response);
                return false;
            } else
            {
                // 如果session中存在登录者实体，则继续
                return true;
            }
        } else
        {
            // 如果不执行过滤，则继续
            return true;
        }
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
