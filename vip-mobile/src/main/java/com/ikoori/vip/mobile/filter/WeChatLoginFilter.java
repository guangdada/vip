package com.ikoori.vip.mobile.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.ikoori.vip.common.constant.Const;
import com.ikoori.vip.mobile.util.WeChatAPI;

public class WeChatLoginFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 不过滤的uri
		String[] ignores = new String[] { "login", "kaptcha" };
		// 请求的uri
		String uri = request.getRequestURI();
		// 是否过滤
		boolean doFilter = true;
		for (String ignore : ignores) {
			if (uri.indexOf(ignore) != -1) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				doFilter = false;
				break;
			}
		}
		if (doFilter) {
			// 执行过滤
			// 从session中获取登录者实体
			Object obj = request.getSession().getAttribute(Const.SESSION_USER_INFO);
			if (null == obj) {
				// 如果session中不存在登录者实体，则弹出框提示重新登录
				// 设置request和response的字符集，防止乱码
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				// 网页授权后回调的地址
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ "/login";
				// 用户最后访问的地址
				String state = UUID.randomUUID().toString();
				request.getSession().setAttribute("state", state);
				//String lastAccessUrl = request.getRequestURL() + "?" + request.getQueryString();
				String lastAccessUrl = request.getRequestURL().toString();
				String redirect_uri = basePath + "?lastAccessUrl=" + lastAccessUrl;
				StringBuffer sb = new StringBuffer(WeChatAPI.weboauth);
				sb.append("?").append("scope=snsapi_userinfo").append("&state=").append(state).append("&redirect_uri=").append(redirect_uri);
				response.sendRedirect(sb.toString());
			} else {
				// 如果session中存在登录者实体，则继续
				filterChain.doFilter(request, response);
			}
		} else {
			// 如果不执行过滤，则继续
			filterChain.doFilter(request, response);
		}
	}
}
