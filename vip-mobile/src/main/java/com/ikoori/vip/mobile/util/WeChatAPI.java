package com.ikoori.vip.mobile.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.constant.Const;
import com.ikoori.vip.common.support.HttpKit;

/**
 * 微信公众平台API调用工具
 */
public class WeChatAPI {

	private WeChatAPI() {
		throw new AssertionError("不能从这里运行");
	}

	/** 获得用户信息 */
	public static final String findUserInfo = "http://krvip.ikoori.com/findUserInfo";
	/** 发起网页授权 */
	public static final String weboauth = "http://krvip.ikoori.com/oauth/weixin/weboauth";

	public static UserInfo getUserInfo(String openid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("openid", openid);
		String resultJson = HttpKit.sendGet(findUserInfo, param);
		JSONObject json = JSONObject.parseObject(EmojiFilter.filterEmoji(resultJson));
		UserInfo userInfo = JSONObject.parseObject(json.get("obj").toString(), UserInfo.class);
		String nickname = EmojiFilter.filterEmoji(userInfo.getNickname());
		if (StringUtils.isBlank(nickname)) {
			String operid6 = StringUtils.right(userInfo.getOpenid(), 6);
			nickname = StringUtils.isBlank(operid6) ? "匿名" : operid6;
		}
		userInfo.setNickname(nickname);
		return userInfo;
	}
	
	public static UserInfo getUserInfo(HttpSession session) {
		Object obj = session.getAttribute(Const.SESSION_USER_INFO);
		if (obj != null) {
			return ((UserInfo) obj);
		}
		return null;
	}
	
	public static String getOpenId(HttpSession session) {
		Object obj = session.getAttribute(Const.SESSION_USER_INFO);
		if (obj != null) {
			return ((UserInfo) obj).getOpenid();
		}
		return null;
	}
	
	

}
