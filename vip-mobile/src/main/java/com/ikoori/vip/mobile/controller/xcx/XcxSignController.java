package com.ikoori.vip.mobile.controller.xcx;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.config.properties.GunsProperties;
import com.ikoori.vip.mobile.config.properties.WechatProperties;
import com.ikoori.vip.mobile.controller.BaseController;

/**
 * 小程序会员接口
 * 
 * @ClassName: XcxMemberController
 * @author: chengxg
 * @date: 2017年11月6日 上午9:30:17
 */
@Controller
@RequestMapping("/xcx/sign")
public class XcxSignController extends BaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	GunsProperties gunsProperties;
	@Autowired
	WechatProperties wechatProperties;
	@Autowired
	DubboConsumer consumer;

	/**
	 * 签到保存
	 * 
	 * @Title: signIn
	 * @param sessionid
	 * @return
	 * @date: 2017年11月13日 上午11:15:37
	 * @author: chengxg
	 */
	@RequestMapping("/signIn")
	@ResponseBody
	public Object signIn(String sessionid) {
		log.info("进入signIn");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");

		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				consumer.getSignApi().get().signIn(unionid);
				result.put("code", "200");
			}
		} catch (Exception e) {
			log.error("签到失败", e);
			// 判断是否为业务异常
			if (StringUtils.isNotBlank(e.getMessage()) && e.getMessage().matches("\\{(.*)\\}")) {
				result.put("msg", JSONObject.parseObject(e.getMessage()).get("msg"));
			} else {
				result.put("msg", "发生未知错误!");
			}
		}
		log.info("结束signIn");
		return result;
	}

	/**
	 * 获得签到信息
	 * 
	 * @Title: signInfo
	 * @param sessionid
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:15:53
	 * @author: chengxg
	 */
	@RequestMapping("/signInfo")
	@ResponseBody
	public Object signInfo(String sessionid) throws Exception {
		log.info("进入signInfo");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				JSONObject obj = consumer.getSignApi().get().getSignInfo(unionid);
				result.put("code", "200");
				result.put("content", obj);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("结束signInfo");
		return result;
	}

}
