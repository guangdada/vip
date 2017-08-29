package com.ikoori.vip.server.modular.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.common.constant.state.RightType;
import com.ikoori.vip.common.persistence.model.CardRight;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.util.WXPayUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.modular.biz.service.ICardRightService;
import com.ikoori.vip.server.modular.biz.service.ICouponFetchService;
import com.ikoori.vip.server.modular.biz.service.IMemberCardService;
import com.ikoori.vip.server.modular.biz.service.IMemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 会员接口
 *
 * @author chengxg
 * @Date 2017-08-04 11:07:42
 */
@Controller
@RequestMapping("/web/member")
public class WebMemberController extends BaseController {
	@Autowired
	IMemberCardService memberCardService;

	@Autowired
	IMemberService memberService;

	@Autowired
	ICardRightService cardRightService;

	@Autowired
	ICouponFetchService couponFetchService;
	
	@Autowired
    GunsProperties gunsProperties;

	@ApiOperation("根据手机号获得会员积分")
	@RequestMapping(value = "getPoints", method = RequestMethod.POST)
	@ResponseBody
	public String getPoints(@ApiParam(value = "店铺编号", required = true) @RequestParam(required = true) String storeNo,
			@ApiParam(value = "手机号", required = true) @RequestParam(required = true) String mobile,
			@ApiParam(value = "签名", required = true) @RequestParam(required = true) String sign) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			boolean isSign = true;
			if(gunsProperties.isCheckSign()){
				Map<String, String> data = new HashMap<String,String>();
				data.put("storeNo", storeNo);
				data.put("mobile", mobile);
				data.put("sign", sign);
				isSign = WXPayUtil.isSignatureValid(data, gunsProperties.getSignKey());
				if(isSign){
					result.put("code", "500");
					result.put("msg", "签名失败");
				}
			}
			if(isSign){
				JSONObject obj = new JSONObject();
				Member member = memberService.selectByMobileAndStoreNo(mobile, storeNo);
				obj.put("mobile", member.getMobile());
				obj.put("point", member.getPoints());
				result.put("content", obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return JSONObject.toJSONString(result);
	}

	@ApiOperation("根据手机号获得会员卡")
	@RequestMapping(value = "getCards", method = RequestMethod.POST)
	@ResponseBody
	public String getCards(@ApiParam(value = "店铺编号", required = true) @RequestParam(required = true) String storeNo,
			@ApiParam(value = "手机号", required = true) @RequestParam(required = true) String mobile,
			@ApiParam(value = "签名", required = true) @RequestParam(required = true) String sign) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			boolean isSign = true;
			if(gunsProperties.isCheckSign()){
				Map<String, String> data = new HashMap<String,String>();
				data.put("storeNo", storeNo);
				data.put("mobile", mobile);
				data.put("sign", sign);
				isSign = WXPayUtil.isSignatureValid(data, gunsProperties.getSignKey());
				if(isSign){
					result.put("code", "500");
					result.put("msg", "签名失败");
				}
			}
			if(isSign){
				Member member = memberService.selectByMobileAndStoreNo(mobile, storeNo);
				if(member == null){
					result.put("code", "500");
					result.put("msg", "没有找到会员信息");
				}else{
					JSONArray content = initCards(member.getId());
					result.put("content", content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return JSONObject.toJSONString(result);
	}

	private JSONArray initCards(Long memberId) {
		JSONArray content = new JSONArray();
		List<Map<String, Object>> memberCards = memberCardService.selectByMemberId(memberId);
		for (Map<String, Object> mc : memberCards) {
			JSONObject obj = new JSONObject();
			obj.put("cardNumber", mc.get("cardNumber"));
			obj.put("name", mc.get("name"));
			obj.put("description", mc.get("description"));

			Long cardId = Long.valueOf("" + mc.get("cardId"));
			List<CardRight> cardRights = cardRightService.selectByCardId(cardId);
			if (CollectionUtils.isNotEmpty(cardRights)) {
				JSONArray rights = new JSONArray();
				for (CardRight cardRight : cardRights) {
					if (cardRight.getRightType().equals(RightType.DISCOUNT.getCode())) {
						JSONObject right = new JSONObject();
						right.put(RightType.DISCOUNT.getCode(), cardRight.getDiscount());
						rights.add(right);
					}
				}
				obj.put("cardRights", rights);
			}
			content.add(obj);
		}
		return content;
	}

	@ApiOperation("根据手机号获得优惠券")
	@RequestMapping(value = "getCoupon", method = RequestMethod.POST)
	@ResponseBody
	public String getCoupon(@ApiParam(value = "店铺编号", required = true) @RequestParam(required = true) String storeNo,
			@ApiParam(value = "手机号", required = true) @RequestParam(required = true) String mobile,
			@ApiParam(value = "签名", required = true) @RequestParam(required = true) String sign) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			boolean isSign = true;
			if(gunsProperties.isCheckSign()){
				Map<String, String> data = new HashMap<String,String>();
				data.put("storeNo", storeNo);
				data.put("mobile", mobile);
				data.put("sign", sign);
				isSign = WXPayUtil.isSignatureValid(data, gunsProperties.getSignKey());
				if(isSign){
					result.put("code", "500");
					result.put("msg", "签名失败");
				}
			}
			if(isSign){
				Member member = memberService.selectByMobileAndStoreNo(mobile, storeNo);
				JSONArray content = initCoupon(member.getId());
				result.put("content", content);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return JSONObject.toJSONString(result);
	}

	private JSONArray initCoupon(Long memberId) {
		JSONArray content = new JSONArray();
		List<Map<String, Object>> couponFetchs = couponFetchService.selectByMemberId(memberId);
		for (Map<String, Object> mc : couponFetchs) {
			JSONObject obj = new JSONObject();
			obj.put("verifyCode", mc.get("verifyCode"));
			obj.put("type", mc.get("type"));
			obj.put("value", Double.valueOf(""+mc.get("value")) * 100);
			obj.put("availableValue", mc.get("availableValue"));
			obj.put("atLeast", mc.get("originAtLeast"));
			obj.put("description", mc.get("description"));
			content.add(obj);
		}
		return content;
	}

	@ApiOperation("根据手机号获得优惠券、积分、会员卡")
	@RequestMapping(value = "info", method = RequestMethod.POST)
	@ResponseBody
	public String info(@ApiParam(value = "店铺编号", required = true) @RequestParam(required = true) String storeNo,
			@ApiParam(value = "手机号", required = true) @RequestParam(required = true) String mobile,
			@ApiParam(value = "签名", required = true) @RequestParam(required = true) String sign) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			boolean isSign = true;
			if(gunsProperties.isCheckSign()){
				Map<String, String> data = new HashMap<String,String>();
				data.put("storeNo", storeNo);
				data.put("mobile", mobile);
				data.put("sign", sign);
				isSign = WXPayUtil.isSignatureValid(data, gunsProperties.getSignKey());
				if(isSign){
					result.put("code", "500");
					result.put("msg", "签名失败");
				}
			}
			if(isSign){
				JSONObject content = new JSONObject();
				Member member = memberService.selectByMobileAndStoreNo(mobile, storeNo);
				JSONArray cards = initCards(member.getId());
				JSONArray coupons = initCoupon(member.getId());
				content.put("mobile", member.getMobile());
				content.put("point", member.getPoints());
				content.put("cards", cards);
				content.put("coupon", coupons);
				result.put("content", content);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return JSONObject.toJSONString(result);
	}

	public static void main(String[] args) {
		Map<String, Object> point = new HashMap<String, Object>();
		point.put("memberId", 1);
		point.put("mobile", "18508443775");
		point.put("point", 88);
		System.out.println(JSONObject.toJSONString(point));
	}
}
