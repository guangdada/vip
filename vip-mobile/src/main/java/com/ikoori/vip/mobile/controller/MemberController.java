package com.ikoori.vip.mobile.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
import com.ikoori.vip.common.constant.state.PointTradeType;
import com.ikoori.vip.common.constant.tips.ErrorTip;
import com.ikoori.vip.common.constant.tips.SuccessTip;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.util.IpUtil;
import com.ikoori.vip.mobile.config.DubboConsumer;
import com.ikoori.vip.mobile.config.properties.GunsProperties;
import com.ikoori.vip.mobile.config.properties.WechatProperties;
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
public class MemberController extends BaseController{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	GunsProperties gunsProperties;
	@Autowired
	WechatProperties wechatProperties;

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
			String unionid = WeChatAPI.getUnionid(request.getSession());
			if (unionid == null) {
				log.info("unionid==null");
				throw new Exception("登录信息有误");
			}
			
			//获取会员信息
			JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByUnionid(unionid);
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
			String unionid = WeChatAPI.getUnionid(request.getSession());
			if (unionid == null) {
				log.info("unionid == null");
				throw new Exception("登录信息有误");
			}

			JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByUnionid(unionid);
			if (member == null || !member.getBooleanValue("isActive")) {
				throw new Exception("用户没有激活");
			}

			if (!member.getString("mobile").equals(mem.getMobile())) {
				throw new Exception("激活后手机号不能修改");
			}

			// 修改会员信息
			consumer.getMemberInfoApi().get().updateMemberInfoByUnionid(unionid, mem.getMobile(), mem.getName(),
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
		String unionid = WeChatAPI.getUnionid(request.getSession());
		if (unionid == null) {
			log.info("unionid == null");
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
		
		// 验证手机短信验证码是否正确
		String mobileCode1 = request.getSession().getAttribute(Constant.MOBILE_CODE) + "";
		if (StringUtils.isBlank(mobileCode1) || !mobileCode1.equals(mobileCode)) {
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
			// 激活会员信息
			consumer.getMemberInfoApi().get().activeMemberByUnionid(unionid, mem.getMobile(),IpUtil.getIpAddr(request),true);
			// 移除短信码
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
		String imgCode = (String) request.getSession().getAttribute(Constant.IMG_CODE);
		if (mobile.equals("")) {
			log.info(BizExceptionEnum.EMPTY_MOBILE.getMessage());
			return new ErrorTip(BizExceptionEnum.EMPTY_MOBILE);
		} else if (imgCode.equals(code)) {
			request.getSession().setAttribute(Constant.MOBILE_CODE, sendMessage(mobile));
			// 移除图形验证码
			request.getSession().removeAttribute(Constant.IMG_CODE);
			log.info("结束validateCode");
			return new SuccessTip();
		} else {
			log.info("结束validateCode");
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
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
		String unionid = WeChatAPI.getUnionid(request.getSession());
		if (unionid == null) {
			log.info("unionid == null");
			throw new Exception("登录信息有误");
		}
		
		//获取会员积分
		List<Map<String, Object>> points = consumer.getMemberPointApi().get().getMemberPointByUnionid(unionid,0,pageSize);
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
		String unionid = WeChatAPI.getUnionid(request.getSession());
		if (unionid == null) {
			log.info("unionid == null");
			throw new Exception("登录信息有误");
		}
		
		//获取会员优惠券
		List<Map<String, Object>> Coupons = consumer.getMemberCouponApi().get().getMemberCouponByUnionid(unionid,0,pageSize);
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
		String unionid = WeChatAPI.getUnionid(request.getSession());
		if (unionid == null) {
			log.info("unionid == null");
			throw new Exception("登录信息有误");
		}
		
		//获取会员订单信息
		List<Map<String, Object>> orders = consumer.getMemberOrderApi().get().getMemberOrderByUnionid(unionid,0,pageSize);
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
		String unionid = WeChatAPI.getUnionid(request.getSession());
		if (unionid == null) {
			log.info("unionid == null");
			throw new Exception("登录信息有误");
		}
		JSONArray orders=null;
		JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByUnionid(unionid);
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
		String unionid = WeChatAPI.getUnionid(request.getSession());
		if (unionid == null) {
			log.info("unionid == null");
			throw new Exception("登录信息有误");
		}
		
		JSONArray orderDetail= this.getTbOrderByTid(tid);
		map.put("totalPayment", totalPayment);
		map.put("orderDetail", orderDetail);
		log.info("结束onlineOrderDetail");
		return "/member_onlineOrderDetail.html";
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
		String unionid = WeChatAPI.getUnionid(request.getSession());
		if(unionid == null){
			log.info("unionid == null");
			throw new Exception("登录信息有误");
		}
		List<Map<String,Object>> store=consumer.getStoreApi().get().getStore(unionid,0,pageSize);
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
			ret.put("appId", wechatProperties.getAppid());
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
		String unionid = WeChatAPI.getUnionid(request.getSession());
		if(unionid == null){
			log.info("unionid == null");
			throw new Exception("登录信息有误");
		}
		JSONObject obj =consumer.getMemberCardApi().get().selectByMemberId(unionid);
		map.put("card", obj);
		log.info("结束cardDetail");
		return "/member_cardDetail.html";
	}
	
}
