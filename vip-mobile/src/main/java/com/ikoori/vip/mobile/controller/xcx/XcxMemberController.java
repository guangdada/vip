package com.ikoori.vip.mobile.controller.xcx;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.common.constant.cache.Cache;
import com.ikoori.vip.common.support.HttpUtil;
import com.ikoori.vip.core.cache.CacheKit;
import com.ikoori.vip.mobile.config.properties.GunsProperties;
import com.ikoori.vip.mobile.config.properties.WechatProperties;
import com.ikoori.vip.mobile.controller.BaseController;
import com.ikoori.vip.mobile.util.AesCbcUtil;
import com.ikoori.vip.mobile.util.WeChatAPI;

/**
 * 小程序会员接口
 * 
 * @ClassName: XcxMemberController
 * @author: chengxg
 * @date: 2017年11月6日 上午9:30:17
 */
@Controller
@RequestMapping("/xcx/member")
public class XcxMemberController extends BaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	GunsProperties gunsProperties;
	@Autowired
	WechatProperties wechatProperties;

	@RequestMapping(value = "onLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> onLogin(String encryptedData, String iv, String code) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			//登录凭证不能为空
	        if (StringUtils.isBlank(code)) {
	        	result.put("code", 500);
	        	result.put("msg", "code 不能为空");
	            return result;
	        }
			String url = MessageFormat.format(WeChatAPI.jscode2session, wechatProperties.getXcxAppid(),
					wechatProperties.getXcxSecret(), code);
			String msg = HttpUtil.get(url);
			log.info("jscode2session >>>" + msg);
			JSONObject data = JSONObject.parseObject(msg);
			String session_key = data.getString("session_key");
			//登录凭证不能为空
	        if (StringUtils.isBlank(session_key)) {
	        	result.put("code", 500);
	        	result.put("msg", "session_key获取失败");
	            return result;
	        }
			// 解密用戶信息
			String info = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
			log.info("userInfoJSON >>>" + info);
            if (StringUtils.isNotBlank(info)) {
                JSONObject userInfoJSON = JSONObject.parseObject(info);
                /*Map userInfo = new HashMap();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));*/
    			String sessionid = UUID.randomUUID().toString();
    			CacheKit.put(Cache.XCXSESSIONID, sessionid, userInfoJSON);
    			result.put("content", sessionid);
            }
		} catch (Exception e) {
			log.error("", e);
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		log.info("result>>>" + result);
		return result;
	}
}
