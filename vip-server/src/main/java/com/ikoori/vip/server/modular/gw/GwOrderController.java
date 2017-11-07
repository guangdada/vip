package com.ikoori.vip.server.modular.gw;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.ikoori.vip.common.dto.CouponPayDo;
import com.ikoori.vip.common.dto.OrderItemPayDo;
import com.ikoori.vip.common.dto.OrderPayDo;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.util.WXPayUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.modular.biz.service.IOrderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 官网订单接口
 * @ClassName:  GwOrderController
 * @author: chengxg
 * @date:   2017年11月6日 下午3:15:56
 */
@Controller
@RequestMapping("/gw/order")
public class GwOrderController extends BaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	IOrderService orderService;
	@Autowired
    GunsProperties gunsProperties;
	
	@ApiOperation("会员优惠券、积分结算接口")
	@RequestMapping(value = "pay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pay(@ApiParam(value = "店铺编号", required = true) @RequestParam(required = true) String storeNo,
			@ApiParam(value = "openid", required = true) @RequestParam(required = true) String openid,
			@ApiParam(value = "订单号", required = true) @RequestParam(required = true) String orderNo,
			@ApiParam(value = "订单总额", required = true) @RequestParam(required = true) String balanceDue,
			@ApiParam(value = "支付金额", required = true) @RequestParam(required = true) String payment,
			@ApiParam(value = "使用积分", required = true) @RequestParam(required = true) String point,
			@ApiParam(value = "优惠金额", required = true) @RequestParam(required = true) String discount,
			@ApiParam(value = "优惠券明细", required = true) @RequestParam(required = true) String coupons,
			@ApiParam(value = "订单明细", required = true) @RequestParam(required = true) String orderItems,
			@ApiParam(value = "签名", required = true) @RequestParam(required = true) String sign) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>会员优惠券、积分结算接口>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			boolean isSign = true;
			if(gunsProperties.isCheckSign()){
				Map<String, String> data = new HashMap<String,String>();
				data.put("storeNo", storeNo);
				data.put("openid", openid);
				data.put("mobile", orderNo);
				data.put("balanceDue", balanceDue);
				data.put("payment", payment);
				data.put("point", point);
				data.put("discount", discount);
				data.put("coupons", coupons);
				data.put("orderItems", orderItems);
				data.put("sign", sign);
				log.info("请求参数:" + data.toString());
				isSign = WXPayUtil.isSignatureValid(data, gunsProperties.getSignKey());
				if(isSign){
					log.info("签名失败");
					result.put("code", "500");
					result.put("msg", "签名失败");
				}
			}
			if(isSign){
				OrderPayDo orderPayDo = new OrderPayDo();
				orderPayDo.setStoreNo(storeNo);
				orderPayDo.setOpenid(openid);
				orderPayDo.setOrderNo(orderNo);
				orderPayDo.setBalanceDue(Integer.valueOf(balanceDue));
				orderPayDo.setPayment(Integer.valueOf(payment));
				orderPayDo.setDiscount(Integer.valueOf(discount));
				orderPayDo.setPoint(Integer.valueOf(point));
				orderPayDo.setCoupons(JSONArray.parseArray(coupons, CouponPayDo.class));
				orderPayDo.setOrderItems(JSONArray.parseArray(orderItems, OrderItemPayDo.class));
				orderService.saveOrder(orderPayDo);
			}
		} catch (BussinessException e) {
			log.error("",e);
			result.put("code", e.getCode());
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error("",e);
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<会员优惠券、积分结算接口<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return result;
	}
}
