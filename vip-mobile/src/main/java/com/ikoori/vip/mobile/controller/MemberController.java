package com.ikoori.vip.mobile.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.ikoori.vip.common.constant.state.PointTradeType;
import com.ikoori.vip.common.constant.tips.ErrorTip;
import com.ikoori.vip.common.constant.tips.SuccessTip;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.sms.Client;
import com.ikoori.vip.mobile.config.DubboConsumer;
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
	DubboConsumer consumer;

	/** 
	* @Title: info 
	* @Description: 会员信息，会员没激活跳转到会员激活页面，激活跳转到会员信息页面
	* @param @param request
	* @param @param map
	* @param @return    
	* @return String     
	* @throws 
	*/
	@RequestMapping(value="/info",method={RequestMethod.GET,RequestMethod.POST})
	public String info(HttpServletRequest request, Map<String, Object> map) {
		try {
			String openId = WeChatAPI.getOpenId(request.getSession());
			if (openId == null) {
				throw new Exception("登录信息有误");
			}
			
			//获取会员信息
			JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
			if (!(member.getBoolean("isActive"))) {
				map.put("member", member);
				return "/member_register.html";
			} else {
				map.put("member", member);
				return "/member_info.html";
			}
		} catch (Exception e) {
			log.error("会员激活页面跳转失败", e);
			return "redirect:../index";
		}
	}
	
	
	/** 
	* @Title: updateInfo 
	* @Description: 修改会员信息
	* @param @param request
	* @param @param map
	* @param @param mem
	* @param @return    
	* @return Object     
	* @throws 
	*/
	@RequestMapping(value="/updateMemberInfo",method={RequestMethod.POST})
	@ResponseBody
	public Object updateInfo(HttpServletRequest request, Map<String, Object> map,@Valid Member mem) {
		try {
			String openId = WeChatAPI.getOpenId(request.getSession());
			if (openId == null) {
				throw new Exception("登录信息有误");
			}
			
			//修改会员信息
			consumer.getMemberInfoApi().get().updateMemberInofByOpenId(openId, mem.getMobile(), mem.getName(),
					mem.getSex(), mem.getBirthday(), mem.getAddress());
			
			//获取修改后的会员信息
			JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
			map.put("member", member);
		} catch (Exception e) {
			log.error("会员信息修改失败", e);
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
		return new SuccessTip();
	}
	
	
	/** 
	* @Title: updateInfoGet 
	* @Description: 修改会员信息
	* @param @param request
	* @param @param map
	* @param @param mem
	* @param @return
	* @param @throws Exception    
	* @return String     
	* @throws 
	*/
	@RequestMapping(value="/updateMemberInfo",method={RequestMethod.GET})
	public String updateInfoGet(HttpServletRequest request, Map<String, Object> map,@Valid Member mem) throws Exception {
		String openId = WeChatAPI.getOpenId(request.getSession());
		if (openId == null) {
			throw new Exception("登录信息有误");
		}
		
		//获取会员信息
		JSONObject member = consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
		map.put("member", member);
		return "/member_info.html";
	}
	
	
	/** 
	* @Title: registerMember 
	* @Description: 会员激活
	* @param @param request
	* @param @param map
	* @param @param mem
	* @param @param mobileCode 手机短信验证码
	* @param @return    
	* @return Object     
	* @throws 
	*/
	@RequestMapping(value="/registerMember",method={RequestMethod.POST})
	@ResponseBody
	public Object registerMember(HttpServletRequest request, Map<String, Object> map, @Valid Member mem,
			String mobileCode) {
		//验证手机短信验证码是否正确
		String mobileCode1 = (Integer) request.getSession().getAttribute(Constant.MOBILE_CODE) + "";
		Object member = consumer.getMemberInfoApi().get().getMemberByMobile(mem.getMobile());
		if (!(mobileCode1.equals(mobileCode))) {
			return new ErrorTip(BizExceptionEnum.ERROR_MOBILE_CODE);
		}
		
		// 验证手机号是否唯一
		if (member != null) {
			return new ErrorTip(BizExceptionEnum.EXISTED_MOBILE);
		}
		try {
			String openId = WeChatAPI.getOpenId(request.getSession());
			if (openId == null) {
				return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
			}
			
			//更新会员信息
			consumer.getMemberInfoApi().get().updateMemberInofByOpenId(openId, mem.getMobile(), null, 1, null, null);
		} catch (Exception e) {
			log.error("会员激活失败", e);
			e.printStackTrace();
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
		return new SuccessTip();
	}
	
	
	
	/** 
	* @Title: validateCode 
	* @Description: 验证码验证
	* @param @param request
	* @param @param map
	* @param @param code   随机验证码
	* @param @param mobile 手机号
	* @param @return    
	* @return Object     
	* @throws 
	*/
	@RequestMapping(value="/validateCode",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object validateCode(HttpServletRequest request,Map<String, Object> map,
			String code, String mobile) {
		// 验证验证码是否正确，正确则发送手机短信验证码
		String code1 = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (mobile.equals("")) {
			return new ErrorTip(BizExceptionEnum.EMPTY_MOBILE);
		} else if (code1.equals(code)) {
			sendMessage(request, mobile);
			return new SuccessTip();
		} else {
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
	}
	
	
	/** 
	* @Title: sendMessage 
	* @Description: 发送手机短信验证码
	* @param @param request
	* @param @param mobile  手机号 
	* @return void     
	* @throws 
	*/
	public void sendMessage(HttpServletRequest request, String mobile) {
		int max = 999999;
		int min = 100000;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		request.getSession().setAttribute(Constant.MOBILE_CODE, s);
		String content = "【酷锐运动】您的激活验证码是：" + s;
		String result_mt = Client.me().mdSmsSend_u(mobile, content, "", "", "");
		return;
	}
	
	
	/** 
	* @Title: validateMoblie 
	* @Description: 验证手机号是否唯一
	* @param @param request
	* @param @param map
	* @param @param mobile 手机号
	* @param @return    
	* @return Object     
	* @throws 
	*/
	@RequestMapping(value="/validateMobile",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object validateMoblie(HttpServletRequest request, Map<String, Object> map, String mobile) {
		// 手机号是否唯一
		Object member = consumer.getMemberInfoApi().get().getMemberByMobile(mobile);
		if (member == null) {
			return new SuccessTip();
		} else {
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
	}
	
	
	/** 
	* @Title: point 
	* @Description: 会员积分页面
	* @param @param request
	* @param @param map
	* @param @return
	* @param @throws Exception    
	* @return String     
	* @throws 
	*/
	@RequestMapping("/point")
	public String point(HttpServletRequest request, Map<String, Object> map) throws Exception {
		String openId = WeChatAPI.getOpenId(request.getSession());
		if (openId == null) {
			throw new Exception("登录信息有误");
		}
		
		//获取会员积分
		List<Map<String, Object>> points = consumer.getMemberPointApi().get().getMemberPointByOpenId(openId);
		map.put("pointTradeType", PointTradeType.values());
		map.put("points", points);
		return "/member_point.html";
	}
	
	
	/** 
	* @Title: coupon 
	* @Description: 会员优惠券页面
	* @param @param request
	* @param @param map
	* @param @return
	* @param @throws Exception    
	* @return String     
	* @throws 
	*/
	@RequestMapping(value="/coupon",method={RequestMethod.GET,RequestMethod.POST})
	public String coupon(HttpServletRequest request, Map<String, Object> map) throws Exception {
		String openId = WeChatAPI.getOpenId(request.getSession());
		if (openId == null) {
			throw new Exception("登录信息有误");
		}
		
		//获取会员优惠券
		List<Map<String, Object>> Coupons = consumer.getMemberCouponApi().get().getMemberCouponByOpenId(openId);
		map.put("Coupons", Coupons);
		return "/member_coupon.html";
	}
	
	
	/** 
	* @Title: couponDetail 
	* @Description: 会员优惠券详细信息
	* @param @param request
	* @param @param map
	* @param @param couponId 优惠券id
	* @param @param id 优惠券领取id
	* @param @return    
	* @return String     
	* @throws 
	*/
	@RequestMapping(value="/couponDetail",method={RequestMethod.GET,RequestMethod.POST})
	public String couponDetail(HttpServletRequest request, Map<String, Object> map, String couponId, String id) {
		Object couponDetail = consumer.getMemberCouponApi().get()
				.getMemberCouponDetailByCouponId(Long.valueOf(couponId), Long.valueOf(id));
		map.put("couponDetail", couponDetail);
		return "/member_couponDetail.html";
	}
	
	
	/** 
	* @Title: order 
	* @Description: 会员订单页面
	* @param @param request
	* @param @param map
	* @param @return
	* @param @throws Exception    
	* @return String     
	* @throws 
	*/
	@RequestMapping(value = "/order", method = { RequestMethod.GET, RequestMethod.POST })
	public String order(HttpServletRequest request, Map<String, Object> map) throws Exception {
		String openId = WeChatAPI.getOpenId(request.getSession());
		if (openId == null) {
			throw new Exception("登录信息有误");
		}
		
		//获取会员订单信息
		List<Map<String, Object>> orders = consumer.getMemberOrderApi().get().getMemberOrderByOpenId(openId);
		map.put("orders", orders);
		return "/member_order.html";
	}
	
	
	/** 
	* @Title: orderDetail 
	* @Description: 会员订单详情页面
	* @param @param request
	* @param @param map
	* @param @param orderId 订单id
	* @param @return    
	* @return String     
	* @throws 
	*/
	@RequestMapping(value = "/orderDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String orderDetail(HttpServletRequest request, Map<String, Object> map, Long orderId) {
		List<Map<String, Object>> orderDetail = consumer.getMemberOrderApi().get()
				.getMemberOrderDetailByOrderId(orderId);
		map.put("orderDetail", orderDetail);
		return "/member_orderDetail.html";
	}
	
	
	/** 
	* @Title: store 
	* @Description: 附近门店
	* @param @param request
	* @param @param map
	* @param @param lat 纬度
	* @param @param lon 经度
	* @param @return    
	* @return String     
	* @throws 
	*/
	@RequestMapping(value = "/store", method = { RequestMethod.GET, RequestMethod.POST })
	public String store(HttpServletRequest request, Map<String, Object> map, double lat, double lon) {
		/*Object ret = this.getWxConfig(request);
		map.put("ret", ret);*/
		List<Map<String, Object>> store = consumer.getStoreApi().get().loadStore(lat, lon);
		map.put("store", store);
		return "/store.html";
	}
	
	
	/** 
	* @Title: storeDetail 
	* @Description: 附近门店详细信息
	* @param @param request
	* @param @param map
	* @param @param storeId 门店id
	* @param @return    
	* @return String     
	* @throws 
	*/
	@RequestMapping(value = "/storeDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String storeDetail(HttpServletRequest request, Map<String, Object> map, Long storeId) {
		JSONObject storeDetail = consumer.getStoreApi().get().getStoreDetail(storeId);
		map.put("storeDetail", storeDetail);
		return "/storeDetail.html";
	}
	
	
	/** 
	* @Title: getWxConfig 
	* @Description: 微信配置信息
	* @param @param request
	* @param @return    
	* @return Object     
	* @throws 
	*/
	@RequestMapping(value = "/getWxConfig", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getWxConfig(HttpServletRequest request) {
		System.out.println("进入getWxConfig!!");
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			String appId = WeChatAPI.APPID; // 必填，公众号的唯一标识
			String jsapi_ticket = WeChatAPI.getJsApiTicket(request.getSession());
			System.out.println("jsapi_ticket:" + jsapi_ticket);
			String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
			String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
			// 注意这里参数名必须全部小写，且必须有序
			String[] paramArr = new String[] { "jsapi_ticket=" + jsapi_ticket, "noncestr=" + nonceStr,
					"timestamp=" + timestamp };
			Arrays.sort(paramArr);
			// 将排序后的结果拼接成一个字符串
			String sign = paramArr[0].concat("&" + paramArr[1]).concat("&" + paramArr[2]);
			String signature = WeChatAPI.SHA1(sign);
			ret.put("appId", appId);
			ret.put("timestamp", timestamp);
			ret.put("nonceStr", nonceStr);
			ret.put("signature", signature);
			ret.put("jsapi_ticket", jsapi_ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("出去getWxConfig!!" + ret.toString());
		return ret;
	}
}
