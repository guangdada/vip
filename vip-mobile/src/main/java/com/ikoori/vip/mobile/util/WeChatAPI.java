package com.ikoori.vip.mobile.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	public static String APPID= "wx1679d41b832b4e28";

	private WeChatAPI() {
		throw new AssertionError("不能从这里运行");
	}

	/** 获得用户信息 */
	public static final String findUserInfo = "http://krvip.ikoori.com/findUserInfo";
	/** 发起网页授权 */
	public static final String weboauth = "http://krvip.ikoori.com/oauth/weixin/weboauth";
	/** 获得jsApiTicket*/
	public static final String jsapiTicket = "http://krvip.ikoori.com/getJsApiTicket";

	/**
	 * 根据网页授权后得到的openid得到用户信息
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
	
	public static String getJsApiTicket(HttpSession session){
		Map<String, String> param = new HashMap<String, String>();
		param.put("openid", getOpenId(session));
		String ticket = HttpKit.sendGet(findUserInfo, param);
		return ticket;
	}
	
	/**
	 * 取得sessioin中保存的用户信息
	 * @param session
	 * @return
	 */
	public static UserInfo getUserInfo(HttpSession session) {
		Object obj = session.getAttribute(Const.SESSION_USER_INFO);
		if (obj != null) {
			return ((UserInfo) obj);
		}
		return null;
	}
	
	/**
	 * 取得session中保存的openid
	 * @param session
	 * @return
	 */
	public static String getOpenId(HttpSession session) {
		Object obj = session.getAttribute(Const.SESSION_USER_INFO);
		if (obj != null) {
			return ((UserInfo) obj).getOpenid();
		}
		return "1111";
	}
	
	/** 
     * @author：罗国辉 
     * @date： 2015年12月17日 上午9:24:43 
     * @description： SHA、SHA1加密
     * @parameter：   str：待加密字符串
     * @return：  加密串
    **/
    public static String SHA1(String str) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
