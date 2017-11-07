package com.ikoori.vip.mobile.controller.xcx;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.common.constant.cache.Cache;
import com.ikoori.vip.common.support.HttpUtil;
import com.ikoori.vip.core.cache.CacheKit;
import com.ikoori.vip.mobile.config.properties.GunsProperties;
import com.ikoori.vip.mobile.config.properties.WechatProperties;
import com.ikoori.vip.mobile.controller.BaseController;
import com.ikoori.vip.mobile.util.WeChatAPI;

/**
 * 小程序会员接口
 * @ClassName:  XcxMemberController
 * @author: chengxg
 * @date:   2017年11月6日 上午9:30:17
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
	public Map<String, Object> onLogin(@RequestParam(required = true) String code) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			String url = MessageFormat.format(WeChatAPI.jscode2session, wechatProperties.getXcxAppid(),
					wechatProperties.getXcxSecret(), code);
			String msg = HttpUtil.get(url);
			JSONObject data = JSONObject.parseObject(msg);
			String openid = data.getString("openid");
			String session_key = data.getString("session_key");
			String unionid = data.getString("unionid");
			String sessionId = openid + "-" + session_key + "-" + unionid;
			CacheKit.put(Cache.XCXSESSIONID, UUID.randomUUID(), sessionId);
			result.put("content", sessionId);
		} catch (Exception e) {
			log.error("", e);
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return result;
	}
}
