package com.ikoori.vip.mobile.controller.xcx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/xcx/coupon")
public class XcxCouponController extends BaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	GunsProperties gunsProperties;
	@Autowired
	WechatProperties wechatProperties;
	@Autowired
	DubboConsumer consumer;

	/**
	 * 我的优惠券
	 * 
	 * @Title: list
	 * @param sessionid
	 * @param start
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:11:30
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> list(String sessionid, int start) throws Exception {
		log.info("进入list");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				List<Map<String, Object>> obj = consumer.getMemberCouponApi().get().getMemberCouponByUnionid(unionid,
						start, pageSize);
				result.put("code", "200");
				result.put("content", obj);
			}
			log.info("结束list");
		} catch (Exception e) {
			log.error("", e);
		}
		return result;
	}

	/**
	 * 优惠券详情
	 * @Title: detail   
	 * @param sessionid
	 * @param couponId
	 * @param id
	 * @return
	 * @throws Exception
	 * @date:   2017年11月13日 上午11:12:16 
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> detail(String sessionid, Long couponId, Long id) throws Exception {
		log.info("进入detail");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				Object obj = consumer.getMemberCouponApi().get().getMemberCouponDetailByCouponId(Long.valueOf(couponId),
						Long.valueOf(id));
				result.put("code", "200");
				result.put("content", obj);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("结束detail");
		return result;
	}

	/**
	 * 激活现金券
	 * 
	 * @Title: active
	 * @param sessionid
	 * @param verifyCode
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:11:43
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/active", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> active(String sessionid, String verifyCode) throws Exception {
		log.info("进入active");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				consumer.getCouponApi().get().activeCoupon(verifyCode, unionid);
				result.put("code", "200");
				result.put("msg", "该券已经放入您的账户");
			}
		} catch (Exception e) {
			log.error("", e);
			// 判断是否为业务异常
			if (StringUtils.isNotBlank(e.getMessage()) && e.getMessage().matches("\\{(.*)\\}")) {
				result.put("msg", JSONObject.parseObject(e.getMessage()).get("msg"));
			}
		}
		log.info("结束active");
		return result;
	}
}
