package com.ikoori.vip.mobile.controller.xcx;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
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
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.constant.cache.Cache;
import com.ikoori.vip.common.support.HttpUtil;
import com.ikoori.vip.common.util.EmojiFilter;
import com.ikoori.vip.core.cache.CacheKit;
import com.ikoori.vip.mobile.config.DubboConsumer;
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
	@Autowired
	DubboConsumer consumer;

	/**
	 * 小程序登录入口
	 * @Title: onLogin   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @date:   2017年11月9日 下午4:20:14 
	 * @author: chengxg
	 * @return: Map<String,Object>      
	 * @throws
	 */
	@RequestMapping(value = "onLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> onLogin(String encryptedData, String iv, String code) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "请求失败");
		try {
			// 登录凭证不能为空
			if (StringUtils.isBlank(code) || StringUtils.isBlank(encryptedData) || StringUtils.isBlank(iv)) {
				result.put("msg", "code,encryptedData,iv不能为空");
				log.error("code,encryptedData,iv不能为空");
				return result;
			}
			String url = MessageFormat.format(WeChatAPI.jscode2session, wechatProperties.getXcxAppid(),
					wechatProperties.getXcxSecret(), code);
			String msg = HttpUtil.get(url);
			log.info("jscode2session >>>" + msg);
			JSONObject data = JSONObject.parseObject(msg);
			String session_key = data.getString("session_key");
			// 登录凭证不能为空
			if (StringUtils.isBlank(session_key)) {
				result.put("msg", "session_key获取失败");
				return result;
			}
			// 解密用戶信息
			String info = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
			log.info("userInfoJSON >>>" + info);
			if (StringUtils.isNotBlank(info)) {
				JSONObject userInfoJSON = JSONObject.parseObject(info);
				String unionid = userInfoJSON.getString("unionId");
				UserInfo userInfo = new UserInfo();
				userInfo.setOpenid(userInfoJSON.getString("openId"));
				userInfo.setNickname(EmojiFilter.filterEmoji(userInfoJSON.getString("nickName")));
				userInfo.setSex(userInfoJSON.getIntValue("gender"));
				userInfo.setLanguage(userInfoJSON.getString("language"));
				userInfo.setCity(userInfoJSON.getString("city"));
				userInfo.setProvince(userInfoJSON.getString("province"));
				userInfo.setCountry(userInfoJSON.getString("country"));
				userInfo.setHeadimgurl(userInfoJSON.getString("avatarUrl"));
				userInfo.setUnionid(unionid);
				// 新用户生成会员信息
				consumer.getMemberInfoApi().get().saveMemberInfo(userInfo, false);
				String sessionid = UUID.randomUUID().toString();
				CacheKit.put(Cache.XCXSESSIONID, sessionid, unionid);
				result.put("code", "200");
				result.put("content", sessionid);
			}
		} catch (Exception e) {
			log.error("", e);
			result.put("msg", "请求失败");
		}
		log.info("result>>>" + result);
		return result;
	}
	
	/**
	 * 查询会员积分明细
	 * @Title: point   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @date:   2017年11月9日 下午4:19:35 
	 * @author: chengxg
	 * @return: Map<String,Object>      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/point",method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> point(String sessionid, Integer start) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		log.info("进入point");
		if (sessionid == null || start == null) {
			log.error("参数不正确");
			result.put("msg", "参数不正确");
			return result;
		}
		String unionid = CacheKit.get(Cache.XCXSESSIONID, sessionid);
		if (unionid == null) {
			log.error("没有找到sessionid>>" + sessionid);
			result.put("msg", "没有找到sessionid");
			return result;
		}
		// 获取会员积分
		List<Map<String, Object>> points = consumer.getMemberPointApi().get().getMemberPointByUnionid(unionid,start);
		result.put("code", "200");
		result.put("content", points);
		log.info("结束point");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value ="/card",method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> card(String sessionid) throws Exception {
		log.info("进入card");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		if (StringUtils.isBlank(sessionid)) {
			log.error("sessionid不能为空");
			result.put("msg", "sessionid不能为空");
			return result;
		}
		String unionid = CacheKit.get(Cache.XCXSESSIONID, sessionid);
		if (StringUtils.isBlank(unionid)) {
			log.error("没有找到sessionid>>" + sessionid);
			result.put("msg", "没有找到sessionid");
			return result;
		}
		JSONObject obj = consumer.getMemberCardApi().get().getMemberCardByUnionid(unionid);
		result.put("code", "200");
		result.put("content", obj);
		log.info("结束card");
		return result;
	}
}
