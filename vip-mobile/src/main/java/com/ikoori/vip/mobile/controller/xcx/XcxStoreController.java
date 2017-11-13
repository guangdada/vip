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
import com.ikoori.vip.common.constant.cache.Cache;
import com.ikoori.vip.core.cache.CacheKit;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.config.properties.GunsProperties;
import com.ikoori.vip.mobile.config.properties.WechatProperties;
import com.ikoori.vip.mobile.controller.BaseController;

/**
 * 小程序附近门店
 * 
 * @ClassName: XcxStoreController
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: chengxg
 * @date: 2017年11月10日 下午1:27:19
 * 
 * @Copyright: 2017 www.ikoori.com Inc. All rights reserved.
 */
@Controller
@RequestMapping("/xcx/store")
public class XcxStoreController extends BaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	GunsProperties gunsProperties;
	@Autowired
	WechatProperties wechatProperties;
	@Autowired
	DubboConsumer consumer;

	/**
	 * 查询附近门店
	 * 
	 * @Title: list
	 * @param sessionid
	 * @param start
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:11:02
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> list(String sessionid, int start) throws Exception {
		log.info("进入store");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
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
			List<Map<String, Object>> obj = consumer.getStoreApi().get().getStore(unionid, start, pageSize);
			result.put("code", "200");
			result.put("content", obj);
			log.info("结束store");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 门店详情
	 * 
	 * @Title: detail
	 * @param sessionid
	 * @param storeId
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:10:54
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> detail(String sessionid, Long storeId) throws Exception {
		log.info("进入detail");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
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
			JSONObject obj = consumer.getStoreApi().get().getStoreDetail(storeId);
			result.put("code", "200");
			result.put("content", obj);
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("结束detail");
		return result;
	}

	/**
	 * 门店订单
	 * 
	 * @Title: order
	 * @param sessionid
	 * @param start
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:10:46
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/order", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> order(String sessionid, int start) throws Exception {
		log.info("进入order");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				List<Map<String, Object>> orders = consumer.getMemberOrderApi().get().getMemberOrderByUnionid(unionid,
						start, pageSize);
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
	 * 门店订单详情
	 * 
	 * @Title: orderDetail
	 * @param sessionid
	 * @param orderId
	 * @return
	 * @throws Exception
	 * @date: 2017年11月13日 上午11:16:20
	 * @author: chengxg
	 */
	@ResponseBody
	@RequestMapping(value = "/orderDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> orderDetail(String sessionid, Long orderId) throws Exception {
		log.info("进入orderDetail");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "500");
		result.put("msg", "网络开了小差");
		try {
			String unionid = getUnionid(sessionid, result);
			if (StringUtils.isNotBlank(unionid)) {
				List<Map<String, Object>> orderDetail = consumer.getMemberOrderApi().get()
						.getMemberOrderDetailByOrderId(orderId);
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
