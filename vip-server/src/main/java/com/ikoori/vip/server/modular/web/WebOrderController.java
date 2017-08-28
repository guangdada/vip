package com.ikoori.vip.server.modular.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.common.dto.CouponPayDo;
import com.ikoori.vip.common.dto.OrderItemPayDo;
import com.ikoori.vip.common.dto.OrderPayDo;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.modular.biz.service.IOrderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 订单接口
 *
 * @author chengxg
 * @Date 2017-08-04 11:07:42
 */
@Controller
@RequestMapping("/web/order")
public class WebOrderController extends BaseController {
	@Autowired
	IOrderService orderService;
	
	@ApiOperation("会员优惠券、积分结算接口")
	@RequestMapping(value = "pay", method = RequestMethod.POST)
	@ResponseBody
	public String pay(@ApiParam(value = "店铺编号", required = true) String storeNo,
			@ApiParam(value = "手机号", required = true) String mobile,
			@ApiParam(value = "订单号", required = true) String orderNo,
			@ApiParam(value = "订单总额", required = true) String balanceDue,
			@ApiParam(value = "支付金额", required = true) String payment,
			@ApiParam(value = "使用积分", required = true) String point,
			@ApiParam(value = "优惠金额", required = true) String discount,
			@ApiParam(value = "优惠券明细", required = true) String coupons,
			@ApiParam(value = "订单明细", required = true) String orderItems,
			@ApiParam(value = "签名", required = true) String sign) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			OrderPayDo orderPayDo = new OrderPayDo();
			orderPayDo.setStoreNo(storeNo);
			orderPayDo.setMobile(mobile);
			orderPayDo.setOrderNo(orderNo);
			orderPayDo.setBalanceDue(Integer.valueOf(balanceDue));
			orderPayDo.setPayment(Integer.valueOf(payment));
			orderPayDo.setDiscount(Integer.valueOf(discount));
			orderPayDo.setPoint(Integer.valueOf(point));
			orderPayDo.setCoupons(JSONArray.parseArray(coupons, CouponPayDo.class));
			orderPayDo.setOrderItems(JSONArray.parseArray(orderItems, OrderItemPayDo.class));
			orderService.saveOrder(orderPayDo);
		} catch (BussinessException e) {
			e.printStackTrace();
			result.put("code", e.getCode());
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return JSONObject.toJSONString(result);
	}
}