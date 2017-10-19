package com.ikoori.vip.server.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.support.HttpKit;
import com.ikoori.vip.common.util.EmojiFilter;

/**
 * 微信公众平台API调用工具
 */
public class WeChatAPI {
	/**
	 * 保存用于openId
	 */
	public static String SESSION_USER_INFO = "userInfo";

	/**
	 * 保存网页授权state
	 */
	public static String SESSION_USER_STATE = "state";
	/**
	 * 酷锐运动服务号appid
	 */
	public static String APPID = "wx71e66431bed0303e";
	
	/**
	 * 酷锐运动服务号secret
	 */
	public static String secret = "bc0c1bade2341c8c544126fe1cd114eb";

	private WeChatAPI() {
		throw new AssertionError("不能从这里运行");
	}

	/** 获得用户信息 */
	public static final String findUserInfo = "http://krvip.ikoori.com/findUserInfo";
	/** 发起网页授权 */
	public static final String weboauth = "http://krvip.ikoori.com/oauth/weixin/weboauth";
	/** 获得jsApiTicket */
	public static final String jsapiTicket = "http://krvip.ikoori.com/getJsApiTicket";
	/** 获得accesstoken */
	public static final String accesstoken = "http://krvip.ikoori.com/weixin/accesstoken";
	

	/**
	 * 根据网页授权后得到的openid得到用户信息
	 * 
	 * @param openid
	 * @return
	 */
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

	
	public static void main(String[] args) {
		/*Map<String, String> param = new HashMap<String, String>();
		param.put("storeNo", "1869eb3b63494579a68fbb20b10fc506");
		param.put("mobile", "18508443775");
		param.put("sign", "1");
		String resultJson = HttpKit.sendPost("http://localhost/web/member/getCards", param);
		System.out.println("");
		JSONObject obj = JSONObject.parseObject(resultJson);
		System.out.println(obj.get("msg"));*/
		
		//System.out.print(getMenu());
	}
}
