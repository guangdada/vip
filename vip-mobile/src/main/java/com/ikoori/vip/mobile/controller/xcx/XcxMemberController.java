package com.ikoori.vip.mobile.controller.xcx;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.constant.cache.Cache;
import com.ikoori.vip.common.persistence.model.Member;
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
	 * 
	 * @Title: onLogin
	 * @param encryptedData
	 * @param iv
	 * @param code
	 * @return
	 * @date: 2017年11月13日 上午11:12:58
	 * @author: chengxg
	 */
	@RequestMapping(value = "onLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> onLogin(String encryptedData, String iv, String code) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
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
		}
		log.info("result>>>" + result);
		return result;
	}

	/**
	 * 我的资料
	 * 
	 * @Title: info
	 * @param sessionid
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:13:05
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> info(String sessionid) throws Exception {
		log.info("进入info");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				// 获取会员信息
				JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByUnionid(unionid);
				result.put("code", "200");
				result.put("content", member);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("结束info");
		return result;
	}

	/**
	 * 验证图形码
	 * 
	 * @Title: validateCode
	 * @param sessionid
	 * @param code
	 * @param mobile
	 * @return
	 * @date: 2017年11月13日 上午11:13:28
	 * @author: chengxg
	 */
	@RequestMapping(value = "/validateCode", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object validateCode(String sessionid, String code, String mobile) {
		log.info("进入validateCode");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				// 验证验证码是否正确，正确则发送手机短信验证码
				String imageCode = CacheKit.get(Cache.XCXIMGCODE, sessionid);
				if (StringUtils.isBlank(imageCode) || !imageCode.equals(code)) {
					result.put("msg", "验证码错误");
					return result;
				}
				CacheKit.put(Cache.XCXMSGCODE, sessionid, sendMessage(mobile));
				// 移除图形码
				CacheKit.remove(Cache.XCXIMGCODE, sessionid);
				result.put("code", "200");
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return result;
	}

	/**
	 * 修改我的资料
	 * 
	 * @Title: update
	 * @param sessionid
	 * @param mem
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:13:47
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> update(String sessionid, @Valid Member mem) throws Exception {
		log.info("进入update");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				// 修改会员信息
				consumer.getMemberInfoApi().get().updateMemberInfoByUnionid(unionid, mem.getMobile(), mem.getName(),
						mem.getSex(), mem.getBirthday(), mem.getAddress(), mem.getArea());
				result.put("code", "200");
			}
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("结束update");
		return result;
	}

	/**
	 * 会员激活
	 * 
	 * @Title: register
	 * @param sessionid
	 * @param mobile
	 * @param mobileCode
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:13:57
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> register(String sessionid, String mobile, String mobileCode) throws Exception {
		log.info("进入register");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				String msgCode = CacheKit.get(Cache.XCXMSGCODE, sessionid);
				if (StringUtils.isBlank(msgCode) || !msgCode.equals(mobileCode)) {
					log.error("短信验证码错误");
					result.put("msg", "短信验证码错误");
					return result;
				}
				// 验证手机号是否唯一
				Object member = consumer.getMemberInfoApi().get().getMemberByMobile(mobile);
				if (member != null) {
					log.info("手机号已经存在>>>" + mobile);
					result.put("msg", "手机号已经存在");
				}
				// 激活会员信息
				consumer.getMemberInfoApi().get().activeMemberByUnionid(unionid, mobile, "", false);
				// 移除短信码
				CacheKit.remove(Cache.XCXMSGCODE, sessionid);
				result.put("code", "200");
			}
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("结束register");
		return result;
	}

	/**
	 * 查询会员积分明细
	 * 
	 * @Title: point
	 * @param sessionid
	 * @param start
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:14:04
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/point", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> point(String sessionid, Integer start) throws Exception {
		log.info("进入point");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			if (sessionid == null || start == null) {
				log.error("参数不正确");
				result.put("msg", "参数不正确");
				return result;
			}
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				// 获取会员积分
				List<Map<String, Object>> points = consumer.getMemberPointApi().get().getMemberPointByUnionid(unionid,
						start, pageSize);
				result.put("code", "200");
				result.put("content", points);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("结束point");
		return result;
	}

	/**
	 * 首页查询会员卡信息
	 * 
	 * @Title: card
	 * @param sessionid
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:14:12
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/card", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> card(String sessionid) throws Exception {
		log.info("进入card");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				JSONObject obj = consumer.getMemberCardApi().get().getMemberCardByUnionid(unionid);
				result.put("code", "200");
				result.put("content", obj);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("结束card");
		return result;
	}

	/**
	 * 会员卡详情
	 * @Title: cardDetail   
	 * @param sessionid
	 * @return
	 * @throws Exception
	 * @date:   2017年11月13日 上午11:14:38 
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/cardDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> cardDetail(String sessionid) throws Exception {
		log.info("进入cardDetail");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				JSONObject obj = consumer.getMemberCardApi().get().selectByMemberId(unionid);
				result.put("code", "200");
				result.put("content", obj);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("结束cardDetail");
		return result;
	}

	/**
	 * 线上订单
	 * 
	 * @Title: order
	 * @param sessionid
	 * @param start
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:12:46
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/onlineOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> order(String sessionid, int start) throws Exception {
		log.info("进入order");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				JSONArray orders = null;
				JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByUnionid(unionid);
				if (StringUtils.isNotBlank(member.getString("mobile"))) {
					orders = getTbOrderByMobile(member.getString("mobile"));
				}
				result.put("code", "200");
				result.put("content", orders);
			}
			log.info("结束order");
		} catch (Exception e) {
			log.error("", e);
		}
		return result;
	}

	/**
	 * 线上订单详情
	 * @Title: orderDetail   
	 * @param sessionid
	 * @param tid
	 * @return
	 * @throws Exception
	 * @date:   2017年11月13日 上午11:15:21 
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/onlineOrderDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> orderDetail(String sessionid, String tid) throws Exception {
		log.info("进入orderDetail");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				JSONArray orderDetail = getTbOrderByTid(tid);
				result.put("code", "200");
				result.put("content", orderDetail);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("结束orderDetail");
		return result;
	}
}
