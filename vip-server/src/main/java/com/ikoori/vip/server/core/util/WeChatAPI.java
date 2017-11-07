package com.ikoori.vip.server.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.support.HttpKit;
import com.ikoori.vip.common.support.HttpUtil;
import com.ikoori.vip.common.util.EmojiFilter;

/**
 * 微信公众平台API调用工具
 */
public class WeChatAPI {

	private WeChatAPI() {
		throw new AssertionError("不能从这里运行");
	}

	/** 获得用户信息 */
	public static final String findUserInfo = "http://krvip.ikoori.com/findUserInfo";
	/** 获得accesstoken */
	public static final String accesstoken = "http://krvip.ikoori.com/weixin/accesstoken";
	/** 批量获取信息 */
	public static final String batchget = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";
	

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
	
	public static String getAccesstoken(){
		String token = HttpKit.sendGet(WeChatAPI.accesstoken, null);
		if (StringUtils.isNotBlank(token)) {
			JSONObject obj = JSONObject.parseObject(token);
			return obj.getString("obj");
		}
		return "";
	}
	
	public static String batchget(String data){
		String url = WeChatAPI.batchget + "?access_token=" + WeChatAPI.getAccesstoken();
		try {
			String result = HttpUtil.wpost(url, data);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		
		String url = WeChatAPI.batchget + "?access_token=" + WeChatAPI.getAccesstoken();
		JSONObject oo = new JSONObject();
		
		JSONArray a = new JSONArray();
		JSONObject sobj = new JSONObject();
		sobj.put("openid", "o19yZs3bzlU0Hk5fat_xa-CmG6E4");
		sobj.put("lang", "zh_CN");
		a.add(sobj);
		
		JSONObject sobj1 = new JSONObject();
		sobj1.put("openid", "o19yZs-kTAu1omBUVgtS3XeL_AXY");
		sobj1.put("lang", "zh_CN");
		a.add(sobj1);
		
		JSONObject sobj2 = new JSONObject();
		sobj2.put("openid", "o19yZs2aNm71Nofvb9hc4FdGbhQc");
		sobj2.put("lang", "zh_CN");
		a.add(sobj2);
		
		JSONObject sobj3 = new JSONObject();
		sobj3.put("openid", "o19yZs3SUmLFaSkABYVImJ-mr7gY");
		sobj3.put("lang", "zh_CN");
		a.add(sobj3);
		
		oo.put("user_list", a);
		
		try {
			String result = HttpUtil.wpost(url, oo.toJSONString());
			JSONObject obj = JSONObject.parseObject(result);
			JSONArray users = (JSONArray) obj.get("user_info_list");
			for (int i = 0; i < users.size(); i++) {
				JSONObject dd  = (JSONObject) users.get(i);
				System.out.println(dd.get("unionid"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
