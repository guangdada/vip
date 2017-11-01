package com.ikoori.vip.mobile.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.constant.state.PointTradeType;
import com.ikoori.vip.common.constant.tips.ErrorTip;
import com.ikoori.vip.common.constant.tips.SuccessTip;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.sms.Client;
import com.ikoori.vip.common.support.HttpKit;
import com.ikoori.vip.common.util.IpUtil;
import com.ikoori.vip.common.util.MD5;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.config.properties.GunsProperties;
import com.ikoori.vip.mobile.constant.Constant;
import com.ikoori.vip.mobile.util.WeChatAPI;


/**
 * @ClassName: MemberController
 * @Description:会员控制器
 * @author :huanglin
 * @date 2017年9月8日
 * 
 */
@Controller
@RequestMapping("/member")
public class MemberController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	GunsProperties gunsProperties;

	@Autowired
	DubboConsumer consumer;

	/** 
	* @Title: info 
	* @Description: 会员信息，会员没激活跳转到会员激活页面，激活跳转到会员信息页面
	* @param  request
	* @param  map
	* @return String     
	* @throws 
	*/
	@RequestMapping(value="/info",method={RequestMethod.GET,RequestMethod.POST})
	public String info(HttpServletRequest request, Map<String, Object> map) {
		try {
			log.info("进入info");
			String openId = WeChatAPI.getOpenId(request.getSession());
			if (openId == null) {
				log.info("openId==null");
				throw new Exception("登录信息有误");
			}
			
			//获取会员信息
			JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
			if (!(member.getBoolean("isActive"))) {
				map.put("member", member);
				log.info("结束info");
				return "/member_register.html";
			} else {
				map.put("member", member);
				log.info("结束info");
				return "/member_info.html";
			}
		} catch (Exception e) {
			log.error("会员激活页面跳转失败", e);
			log.info("结束info");
			return "redirect:/index";
		}
	}
	
	
	/** 
	* @Title: updateInfo 
	* @Description: 修改会员信息
	* @param request
	* @param  map
	* @param  mem
	* @return Object     
	* @throws 
	*/
	@RequestMapping(value="/updateMemberInfo",method={RequestMethod.POST})
	@ResponseBody
	public Object updateInfo(HttpServletRequest request, Map<String, Object> map,@Valid Member mem) {
		try {
			log.info("进入updateInfo");
			String openId = WeChatAPI.getOpenId(request.getSession());
			if (openId == null) {
				log.info("openId == null");
				throw new Exception("登录信息有误");
			}

			JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
			if (member == null || !member.getBooleanValue("isActive")) {
				throw new Exception("用户没有激活");
			}

			if (!member.getString("mobile").equals(mem.getMobile())) {
				throw new Exception("激活后手机号不能修改");
			}

			// 修改会员信息
			consumer.getMemberInfoApi().get().updateMemberInfoByOpenId(openId, mem.getMobile(), mem.getName(),
					mem.getSex(), mem.getBirthday(), mem.getAddress(), mem.getArea());

		} catch (Exception e) {
			log.error("会员信息修改失败", e);
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
		log.info("结束updateInfo");
		return new SuccessTip();
	}
	
	/** 
	* @Title: registerMember 
	* @Description: 会员激活
	* @param  request
	* @param  map
	* @param  mem
	* @param  mobileCode 手机短信验证码
	* @return Object     
	* @throws 
	*/
	@RequestMapping(value="/registerMember",method={RequestMethod.POST})
	@ResponseBody
	public Object registerMember(HttpServletRequest request, Map<String, Object> map, @Valid Member mem,
			String mobileCode) {
		log.info("进入registerMember");
		String openId = WeChatAPI.getOpenId(request.getSession());
		if (openId == null) {
			log.info("openId == null");
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
		
		// 验证手机短信验证码是否正确
		String mobileCode1 = (Integer) request.getSession().getAttribute(Constant.MOBILE_CODE) + "";
		if (!(mobileCode1.equals(mobileCode))) {
			log.info(BizExceptionEnum.ERROR_MOBILE_CODE.getMessage());
			return new ErrorTip(BizExceptionEnum.ERROR_MOBILE_CODE);
		}
		
		// 验证手机号是否唯一
		Object member = consumer.getMemberInfoApi().get().getMemberByMobile(mem.getMobile());
		if (member != null) {
			log.info("member != null");
			return new ErrorTip(BizExceptionEnum.EXISTED_MOBILE);
		}
		
		try {
			// 更新会员信息
			consumer.getMemberInfoApi().get().activeMemberByOpenId(openId, mem.getMobile(),IpUtil.getIpAddr(request));
			request.getSession().removeAttribute(Constant.MOBILE_CODE);
		} catch (Exception e) {
			log.error("会员激活失败", e);
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
		log.info("结束registerMember");
		return new SuccessTip();
	}
	
	
	
	/** 
	* @Title: validateCode 
	* @Description: 验证码验证
	* @param  request
	* @param  map
	* @param  code   随机验证码
	* @param  mobile 手机号
	* @return Object     
	* @throws 
	*/
	@RequestMapping(value="/validateCode",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object validateCode(HttpServletRequest request,Map<String, Object> map,
			String code, String mobile) {
		log.info("进入validateCode");
		// 验证验证码是否正确，正确则发送手机短信验证码
		String code1 = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (mobile.equals("")) {
			log.info(BizExceptionEnum.EMPTY_MOBILE.getMessage());
			return new ErrorTip(BizExceptionEnum.EMPTY_MOBILE);
		} else if (code1.equals(code)) {
			sendMessage(request, mobile);
			
			request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
			log.info("结束validateCode");
			return new SuccessTip();
		} else {
			log.info("结束validateCode");
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
	}
	
	
	/** 
	* @Title: sendMessage 
	* @Description: 发送手机短信验证码
	* @param  request
	* @param  mobile  手机号 
	* @return void     
	* @throws 
	*/
	public void sendMessage(HttpServletRequest request, String mobile) {
		log.info("进入sendMessage");
		int max = 999999;
		int min = 100000;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		request.getSession().setAttribute(Constant.MOBILE_CODE, s);
		String content = "【酷锐运动】您的激活验证码是：" + s;
		String result_mt = Client.me().mdSmsSend_u(mobile, content, "", "", "");
		log.info("结束sendMessage");
		return;
	}
	
	
	/** 
	* @Title: validateMoblie 
	* @Description: 验证手机号是否唯一
	* @param  request
	* @param  map
	* @param  mobile 手机号
	* @return Object     
	* @throws 
	*/
	@RequestMapping(value="/validateMobile",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object validateMoblie(HttpServletRequest request, Map<String, Object> map, String mobile) {
		log.info("进入validateMoblie");
		// 手机号是否唯一
		Object member = consumer.getMemberInfoApi().get().getMemberByMobile(mobile);
		if (member == null) {
			log.info("member == null 结束validateMoblie");
			return new SuccessTip();
		} else {
			log.info("结束validateMoblie");
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
	}
	
	
	/** 
	* @Title: point 
	* @Description: 会员积分页面
	* @param  request
	* @param  map
	* @param @throws Exception    
	* @return String     
	* @throws 
	*/
	@RequestMapping("/point")
	public String point(HttpServletRequest request, Map<String, Object> map) throws Exception {
		log.info("进入point");
		String openId = WeChatAPI.getOpenId(request.getSession());
		if (openId == null) {
			log.info("openId == null");
			throw new Exception("登录信息有误");
		}
		
		//获取会员积分
		List<Map<String, Object>> points = consumer.getMemberPointApi().get().getMemberPointByOpenId(openId);
		map.put("pointTradeType", PointTradeType.values());
		map.put("points", points);
		log.info("结束point");
		return "/member_point.html";
	}
	
	
	/** 
	* @Title: coupon 
	* @Description: 会员优惠券页面
	* @param  request
	* @param  map
	* @param @throws Exception    
	* @return String     
	* @throws 
	*/
	@RequestMapping(value="/coupon",method={RequestMethod.GET,RequestMethod.POST})
	public String coupon(HttpServletRequest request, Map<String, Object> map) throws Exception {
		log.info("进入coupon");
		String openId = WeChatAPI.getOpenId(request.getSession());
		if (openId == null) {
			log.info("openId == null");
			throw new Exception("登录信息有误");
		}
		
		//获取会员优惠券
		List<Map<String, Object>> Coupons = consumer.getMemberCouponApi().get().getMemberCouponByOpenId(openId);
		map.put("Coupons", Coupons);
		log.info("结束coupon");
		return "/member_coupon.html";
	}
	
	
	/** 
	* @Title: couponDetail 
	* @Description: 会员优惠券详细信息
	* @param  request
	* @param  map
	* @param  couponId 优惠券id
	* @param  id 优惠券领取id
	* @return String     
	* @throws 
	*/
	@RequestMapping(value="/couponDetail",method={RequestMethod.GET,RequestMethod.POST})
	public String couponDetail(HttpServletRequest request, Map<String, Object> map, String couponId, String id) {
		log.info("进入couponDetail");
		Object couponDetail = consumer.getMemberCouponApi().get()
				.getMemberCouponDetailByCouponId(Long.valueOf(couponId), Long.valueOf(id));
		map.put("couponDetail", couponDetail);
		
		UserInfo userInfo = WeChatAPI.getUserInfo(request.getSession());
		map.put("userInfo", userInfo);
		
		//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		//map.put("basePath", basePath);
		map.put("basePath", gunsProperties.getClientUrl());
		log.info("结束couponDetail");
		return "/member_couponDetail.html";
	}
	
	
	/** 
	* @Title: storeOrder 
	* @Description: 会员门店订单页面
	* @param  request
	* @param  map
	* @param @throws Exception    
	* @return String     
	* @throws 
	*/
	@RequestMapping(value = "/storeOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public String storeOrder(HttpServletRequest request, Map<String, Object> map) throws Exception {
		log.info("进入storeOrder");
		String openId = WeChatAPI.getOpenId(request.getSession());
		if (openId == null) {
			log.info("openId == null");
			throw new Exception("登录信息有误");
		}
		
		//获取会员订单信息
		List<Map<String, Object>> orders = consumer.getMemberOrderApi().get().getMemberOrderByOpenId(openId);
		map.put("orders", orders);
		log.info("结束storeOrder");
		return "/member_order.html";
	}
	
	
	/** 
	* @Title: storeOrderDetail 
	* @Description: 会员门店订单详情页面
	* @param  request
	* @param  map
	* @param  orderId 订单id
	* @return String     
	* @throws 
	*/
	@RequestMapping(value = "/storeOrderDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String storeOrderDetail(HttpServletRequest request, Map<String, Object> map, Long orderId) {
		log.info("进入storeOrderDetail");
		List<Map<String, Object>> orderDetail = consumer.getMemberOrderApi().get()
				.getMemberOrderDetailByOrderId(orderId);
		map.put("orderDetail", orderDetail);
		log.info("结束storeOrderDetail");
		return "/member_orderDetail.html";
	}
	
	
	
	/**   
	 * @Title: onlineOrder   
	 * @date:   2017年9月27日 下午7:10:05 
	 * @author: huanglin
	 * @return: String      
	 * @throws   
	 */  
	@RequestMapping(value = "/onlineOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public String onlineOrder(HttpServletRequest request, Map<String, Object> map) throws Exception {
		log.info("进入onlineOrder");
		String openId = WeChatAPI.getOpenId(request.getSession());
		if (openId == null) {
			log.info("openId == null");
			throw new Exception("登录信息有误");
		}
		JSONArray orders=null;
		JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
		if(StringUtils.isNotBlank(member.getString("mobile"))){
			//orders=this.getTbOrderByMobile("13975171495");
			orders = this.getTbOrderByMobile(member.getString("mobile"));
		}
		map.put("orders", orders);
		log.info("结束onlineOrder");
		return "/member_onlineOrder.html";
	}
	
	/**   
	 * @Title: onlineOrderDetail   
	 * @date:   2017年9月28日 下午2:40:02 
	 * @author: huanglin
	 * @return: String      
	 * @throws   
	 */  
	@RequestMapping(value = "/onlineOrderDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String onlineOrderDetail(HttpServletRequest request, Map<String, Object> map,String tid,String totalPayment) throws Exception {
		log.info("进入onlineOrderDetail");
		String openId = WeChatAPI.getOpenId(request.getSession());
		if (openId == null) {
			log.info("openId == null");
			throw new Exception("登录信息有误");
		}
		
		JSONArray orderDetail=null;
		orderDetail = this.getTbOrderByTid(tid);
		map.put("totalPayment", totalPayment);
		map.put("orderDetail", orderDetail);
		log.info("结束onlineOrderDetail");
		return "/member_onlineOrderDetail.html";
	}
	
	
	/**   
	 * @Title: getTbOrderByMobile   
	 * @date:   2017年9月28日 下午2:40:19 
	 * @author: huanglin
	 * @return: JSONArray      
	 * @throws   
	 */  
	public JSONArray getTbOrderByMobile(String mobile) {
		log.info("进入getTbOrderByMobile");
		String url = "http://api.ikoori.com:8899/dispatch/tbapi";
	/*	String url = "http://192.168.168.194:8089/ikoori_api/dispatch/tbapi";*/
		Map<String, String> asObject = new TreeMap<String, String>();
		asObject.put("agent_id", "1");
		asObject.put("api", "getTbOrderByMobile");
		asObject.put("mobile", mobile);

		String secretkey = "lucy";

		String sign = MD5.signTopRequestNew(asObject, secretkey, false).toUpperCase();
		asObject.put("secretkey", secretkey);

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		NameValuePair p1 = new BasicNameValuePair("secretkey", secretkey);
		parameters.add(p1);

		NameValuePair p2 = new BasicNameValuePair("agent_id", asObject.get("agent_id"));
		parameters.add(p2);

		NameValuePair p3 = new BasicNameValuePair("api", asObject.get("api"));
		parameters.add(p3);

		NameValuePair p4 = new BasicNameValuePair("mobile", asObject.get("mobile"));
		parameters.add(p4);

		NameValuePair p5 = new BasicNameValuePair("sign", sign);
		parameters.add(p5);

		String result = HttpKit.sendAndReciveData(url, parameters);
		JSONObject jsonResult = JSONObject.parseObject(result);
		JSONArray orders = jsonResult.getJSONArray("data");
		
		log.info("结束getTbOrderByMobile");
		return orders;
	}

	public JSONArray getTbOrderByTid(String tid) {
		log.info("进入getTbOrderByTid");
		String url = "http://api.ikoori.com:8899/dispatch/tbapi";
		/*String url = "http://192.168.168.194:8089/ikoori_api/dispatch/tbapi";*/
		Map<String, String> asObject = new TreeMap<String, String>();
		asObject.put("agent_id", "1");
		asObject.put("api", "getTbOrderByTid");
		asObject.put("tid", tid);

		String secretkey = "lucy";

		String sign = MD5.signTopRequestNew(asObject, secretkey, false).toUpperCase();
		asObject.put("secretkey", secretkey);

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		NameValuePair p1 = new BasicNameValuePair("secretkey", secretkey);
		parameters.add(p1);

		NameValuePair p2 = new BasicNameValuePair("agent_id", asObject.get("agent_id"));
		parameters.add(p2);

		NameValuePair p3 = new BasicNameValuePair("api", asObject.get("api"));
		parameters.add(p3);

		NameValuePair p4 = new BasicNameValuePair("tid", asObject.get("tid"));
		parameters.add(p4);

		NameValuePair p5 = new BasicNameValuePair("sign", sign);
		parameters.add(p5);

		String result = HttpKit.sendAndReciveData(url, parameters);
		JSONObject jsonResult = JSONObject.parseObject(result);
		JSONArray orders = jsonResult.getJSONArray("data");
		
		log.info("结束getTbOrderByTid");
		return orders;
	}
	
	/** 按照经纬度查找附近门店
	* @Title: store 
	* @Description: 附近门店
	* @param  request
	* @param  map
	* @param  lat 纬度
	* @param  lon 经度
	* @return String     
	* @throws 
	*//*
	@RequestMapping(value = "/store", method = { RequestMethod.GET, RequestMethod.POST })
	public String store(HttpServletRequest request, Map<String, Object> map, double lat, double lon) {
		Object ret = this.getWxConfig(request);
		map.put("ret", ret);
		List<Map<String, Object>> store = consumer.storeConsumer().get().loadStore(lat, lon);
		map.put("store", store);
		return "/store.html";
	}*/
	
	/**   
	 * @Title: store   
	 * @date:   2017年9月14日 下午2:12:46 
	 * @author: huanglin
	 * @return: String      
	 * @throws   
	 */  
	@RequestMapping(value = "/store", method = { RequestMethod.GET, RequestMethod.POST })
	public String store(HttpServletRequest request, Map<String, Object> map)throws Exception  {
		log.info("进入store");
		String openId = WeChatAPI.getOpenId(request.getSession());
		if(openId == null){
			log.info("openId == null");
			throw new Exception("登录信息有误");
		}
		List<Map<String,Object>> store=consumer.getStoreApi().get().getStore(openId);
		map.put("store", store);
		log.info("结束store");
		return "/store.html";
	}
	
	
	/** 
	* @Title: storeDetail 
	* @Description: 附近门店详细信息
	* @param  request
	* @param  map
	* @param  storeId 门店id
	* @return String     
	* @throws 
	*/
	@RequestMapping(value = "/storeDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String storeDetail(HttpServletRequest request, Map<String, Object> map, Long storeId) {
		log.info("进入storeDetail");
		JSONObject storeDetail = consumer.getStoreApi().get().getStoreDetail(storeId);
		map.put("storeDetail", storeDetail);
		log.info("结束storeDetail");
		return "/storeDetail.html";
	}
	
	
	/** 
	* @Title: getWxConfig 
	* @Description: 微信配置信息
	* @param  request
	* @return Object     
	* @throws 
	*/
	@RequestMapping(value = "/getWxConfig", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getWxConfig(HttpServletRequest request,String jsurl) {
		log.info("进入getWxConfig!!");
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			String jsapi_ticket = WeChatAPI.getJsApiTicket(request.getSession());
			log.info("jsapi_ticket:" + jsapi_ticket);
			String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
			String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
			// 注意这里参数名必须全部小写，且必须有序
			String[] paramArr = new String[] { "jsapi_ticket=" + jsapi_ticket, "noncestr=" + nonceStr,
					"timestamp=" + timestamp,"url=" + jsurl};
			Arrays.sort(paramArr);
			// 将排序后的结果拼接成一个字符串
			String sign = paramArr[0].concat("&" + paramArr[1]).concat("&" + paramArr[2]).concat("&"+paramArr[3]);
			log.info("signStr:"+sign);
			String signature = WeChatAPI.SHA1(sign);
			ret.put("appId", WeChatAPI.APPID);
			ret.put("timestamp", timestamp);
			ret.put("nonceStr", nonceStr);
			ret.put("signature", signature);
			ret.put("jsapi_ticket", jsapi_ticket);
		} catch (Exception e) {
			log.error("",e);
		}
		log.info("出去getWxConfig!!" + ret.toString());
		return ret;
	}

	/**   
	 * @Title: cardDetail   
	 * @date:   2017年9月20日 上午11:33:16 
	 * @author: huanglin
	 * @return: String      
	 * @throws   
	 */  
	@RequestMapping(value = "/cardDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String cardDetail(HttpServletRequest request, Map<String, Object> map, Long storeId)throws Exception  {
		log.info("进入cardDetail");
		String openId = WeChatAPI.getOpenId(request.getSession());
		if(openId == null){
			log.info("openId == null");
			throw new Exception("登录信息有误");
		}
		JSONObject obj =consumer.getMemberCardApi().get().selectByMemberId(openId);
		map.put("card", obj);
		log.info("结束cardDetail");
		return "/member_cardDetail.html";
	}
	
}
