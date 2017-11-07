package com.ikoori.vip.mobile.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

	private WeChatAPI() {
		throw new AssertionError("不能从这里运行");
	}

	/** 获得用户信息 */
	public static final String findUserInfo = "http://krvip.ikoori.com/findUserInfo";
	/** 发起网页授权 */
	public static final String weboauth = "http://krvip.ikoori.com/oauth/weixin/weboauth";
	/** 获得jsApiTicket */
	public static final String jsapiTicket = "http://krvip.ikoori.com/getJsApiTicket";
	
	/** 根据code调用微信接口换取openid和session_key*/
	public static final String jscode2session = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
	

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

	/**
	 * 获得ticket
	 * @Title: getJsApiTicket   
	 * @param session
	 * @return
	 * @date:   2017年10月11日 下午10:05:13 
	 * @author: chengxg
	 */
	public static String getJsApiTicket(HttpSession session) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("openid", getOpenId(session));
		String ticket = HttpKit.sendGet(jsapiTicket, param);
		if (StringUtils.isNotBlank(ticket)) {
			JSONObject obj = JSONObject.parseObject(ticket);
			return obj.getString("obj");
		}
		return "";
	}
	
	public static String getMenu() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token",
				"xEgKt1KE3obu8yQHIZtsNK3x_XZwUBm57xC-IKsUqIB1nkeNUCHnePkpsCyKXII9pOcrIKkO1HItii_w31AyE2190nM6eGGBMG56mceRyXF7BcSk6-kBELFUacUtbSzjZUNhCHARMF");
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get";
		return HttpKit.sendGet(url, param);
	}
	
	/**
	 * 取得sessioin中保存的用户信息
	 * 
	 * @param session
	 * @return
	 */
	public static UserInfo getUserInfo(HttpSession session) {
		Object obj = session.getAttribute(SESSION_USER_INFO);
		if (obj != null) {
			return ((UserInfo) obj);
		}
		return null;
	}

	/**
	 * 取得session中保存的openid
	 * 
	 * @param session
	 * @return
	 */
	public static String getOpenId(HttpSession session) {
		Object obj = session.getAttribute(SESSION_USER_INFO);
		if (obj != null) {
			return ((UserInfo) obj).getOpenid();
		}
		//return "o19yZsw5CT7CDk_ikBRiGNbyu7Tw";
		return null;
	}
	
	
	/**
	 * 取得session中保存的unionid
	 * 
	 * @param session
	 * @return
	 */
	public static String getUnionid(HttpSession session) {
		Object obj = session.getAttribute(SESSION_USER_INFO);
		if (obj != null) {
			return ((UserInfo) obj).getUnionid();
		}
		//return "o19yZsw5CT7CDk_ikBRiGNbyu7Tw";
		return null;
	}

	/**
	 * 签名
	 * @Title: SHA1   
	 * @param str
	 * @return
	 * @date:   2017年10月11日 下午10:03:49 
	 * @author: chengxg
	 */
	public static String SHA1(String str) {
		String signature = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(str.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signature;
	}
	
	/**
	 * 字符串加密辅助方法
	 * @Title: byteToHex   
	 * @param hash
	 * @return
	 * @date:   2017年10月11日 下午10:04:23 
	 * @author: chengxg
	 */
    public static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
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
