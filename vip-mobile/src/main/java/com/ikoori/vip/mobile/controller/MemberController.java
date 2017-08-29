package com.ikoori.vip.mobile.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.common.constant.tips.ErrorTip;
import com.ikoori.vip.common.constant.tips.SuccessTip;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.mobile.config.DubboConsumer;

@Controller
@RequestMapping("/member")
public class MemberController {
	//@Reference(version = "1.0.0")
	//private MemberService memberService;
	 @Autowired
	 DubboConsumer consumer;


	@RequestMapping("/login")
	@ResponseBody
	public String login() {
		//memberService.test();
		JSONObject obj = consumer.personConsumer().get().test("成");
		return obj.getString("name");
	}
	
	@RequestMapping(value="/info",method={RequestMethod.GET,RequestMethod.POST})
	public String info(HttpServletRequest request, Map<String, Object> map) {
		String openId = "1111";
		JSONObject member=consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
		map.put("member", member);
		return "/member_info.html";
	}
	@RequestMapping(value="/updateMemberInfo",method={RequestMethod.POST})
	@ResponseBody
	public Object updateInfo(HttpServletRequest request, Map<String, Object> map,@Valid Member mem) {
		try {
			String openId = "1111";
			JSONObject member=consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
			map.put("member", member);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
		    return new SuccessTip();
	}
	@RequestMapping(value="/updateMemberInfo",method={RequestMethod.GET})
	public String updateInfoGet(HttpServletRequest request, Map<String, Object> map,@Valid Member mem) {
		String openId = "1111";
		JSONObject member=consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
		map.put("member", member);
		return "/member_info.html";
	}
	@RequestMapping("/register")
	public String register(HttpServletRequest request, Map<String, Object> map) {
		return "/member_register.html";
	}
	@RequestMapping("/point")
	public String point(HttpServletRequest request, Map<String, Object> map) {
		String openId="1111";
		List<Map<String, Object>> points=consumer.getMemberPointApi().get().getMemberPointByOpenId(openId);
		map.put("points", points);
		return "/member_point.html";
	}
	@RequestMapping(value="/coupon",method={RequestMethod.GET,RequestMethod.POST})
	public String coupon(HttpServletRequest request, Map<String, Object> map) {
		String openId = "1111";
		List<Map<String, Object>> Coupons=consumer.getMemberCouponApi().get().getMemberCouponByOpenId(openId);
		map.put("Coupons", Coupons);
		return "/member_coupon.html";
	}
	@RequestMapping(value="/couponDetail",method={RequestMethod.GET,RequestMethod.POST})
	public String couponDetail(HttpServletRequest request, Map<String, Object> map,String couponId,String id) {
		Object couponDetail=consumer.getMemberCouponApi().get().getMemberCouponDetailByCouponId(Long.valueOf(couponId), Long.valueOf(id));
		map.put("couponDetail", couponDetail);
		return "/member_couponDetail.html";
	}
	@RequestMapping(value="/order",method={RequestMethod.GET,RequestMethod.POST})
	public String order(HttpServletRequest request, Map<String, Object> map) {
		String openId="1111";
		List<Map<String,Object>> orders=consumer.getMemberOrderApi().get().getMemberOrderByOpenId(openId);
		map.put("orders", orders);
		return "/member_order.html";
	}
	@RequestMapping(value="/orderDetail",method={RequestMethod.GET,RequestMethod.POST})
	public String orderDetail(HttpServletRequest request, Map<String, Object> map,Long orderId) {
		List<Map<String,Object>> orderDetail=consumer.getMemberOrderApi().get().getMemberOrderDetailByOrderId(orderId);
		map.put("orderDetail", orderDetail);
		return "/member_orderDetail.html";
	}
	@RequestMapping("/store")
	public String store(HttpServletRequest request, Map<String, Object> map) {
		return "/store.html";
	}
	@RequestMapping("/demo")
	public String demo(HttpServletRequest request, Map<String, Object> map) {
		return "/demo.html";
	}
	@RequestMapping("/sorting")
	public String sorting(HttpServletRequest request, Map<String, Object> map) {
		return "/sorting.html";
	}
	@RequestMapping("/select")
	public String select(HttpServletRequest request, Map<String, Object> map) {
		return "/select.html";
	}
	
}
