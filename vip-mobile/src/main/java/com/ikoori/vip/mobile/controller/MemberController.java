package com.ikoori.vip.mobile.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.Session;

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
		if(member!=null){
			if(!(member.getBoolean("isActive"))){
				map.put("member", member);
				return "/member_register.html";
			}else if(member.getBoolean("isActive")){
				map.put("member", member);
				return "/member_info.html";
			}
		}
		map.put("member", member);
		return "/member_info.html";
	}
	@RequestMapping(value="/updateMemberInfo",method={RequestMethod.POST})
	@ResponseBody
	public Object updateInfo(HttpServletRequest request, Map<String, Object> map,@Valid Member mem) {
		String openId = "1111";
		try {
			consumer.getMemberInfoApi().get().updetaMemberInofByOpenId(openId, mem.getMobile(), mem.getName(), mem.getSex(), mem.getBirthday(), mem.getAddress());
			JSONObject member=consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
			map.put("member", member);
		} catch (Exception e) {
			JSONObject member=consumer.getMemberInfoApi().get().getMemberInfoByOpenId(openId);
			map.put("member", member);
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
	@RequestMapping(value="/registerMember",method={RequestMethod.POST})
	@ResponseBody
	public Object registerMember(HttpServletRequest request, Map<String, Object> map,@Valid Member mem,String mobileCode) {
		String mobileCode1=(Integer)request.getSession().getAttribute(Constant.MOBILE_CODE)+"";
		Object member=consumer.getMemberInfoApi().get().getMemberByMobile(mem.getMobile());
		if(!(mobileCode1.equals(mobileCode))){
			return new ErrorTip(BizExceptionEnum.ERROR_MOBILE_CODE);
		}
		//验证手机号是否唯一
		if(member!=null){
			 return new ErrorTip(BizExceptionEnum.EXISTED_MOBILE);
	        }
		try {
			String openId = "1112";
			consumer.getMemberInfoApi().get().updetaMemberInofByOpenId(openId, mem.getMobile(), mem.getName(), mem.getSex(), mem.getBirthday(), mem.getAddress());
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
		}
			return new SuccessTip();
	}
	@RequestMapping("/register")
	public String register(HttpServletRequest request, Map<String, Object> map) {
		return "/member_register.html";
	}
	@RequestMapping(value="/validateCode",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object validateCode(HttpServletRequest request,HttpServletResponse response, Map<String, Object> map,String code,String mobile) {
		//验证验证码是否正确
		String code1 = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		 if(mobile.equals("")){
			 return new ErrorTip(BizExceptionEnum.EMPTY_MOBILE);
		 }else if(code1.equals(code)){
			 sendMessage(request,mobile);
			 return new SuccessTip(); 
		 }else{
			 return new ErrorTip(BizExceptionEnum.SERVER_ERROR); 
		 }
	}
	public void sendMessage(HttpServletRequest request,String mobile){
		int max=999999;
        int min=100000;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
		request.getSession().setAttribute(Constant.MOBILE_CODE,s);
		String content="【酷锐运动】您的激活验证码是："+s;
		String result_mt = Client.me().mdSmsSend_u(mobile, content, "", "", "");
		return;
	}
	@RequestMapping(value="/validateMobile",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object validateMoblie(HttpServletRequest request, Map<String, Object> map,String mobile) {
		//手机号是否唯一
		Object member=consumer.getMemberInfoApi().get().getMemberByMobile(mobile);
        if(member==null){
          return new SuccessTip();
        }else{
          return new ErrorTip(BizExceptionEnum.SERVER_ERROR);
        }
	}
	@RequestMapping("/point")
	public String point(HttpServletRequest request, Map<String, Object> map) {
		String openId="1111";
		List<Map<String, Object>> points=consumer.getMemberPointApi().get().getMemberPointByOpenId(openId);
		map.put("pointTradeType", PointTradeType.values());
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
