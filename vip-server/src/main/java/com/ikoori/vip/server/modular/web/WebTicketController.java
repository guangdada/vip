package com.ikoori.vip.server.modular.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.common.persistence.model.Ticket;
import com.ikoori.vip.common.util.WXPayUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.modular.biz.service.IMemberService;
import com.ikoori.vip.server.modular.biz.service.ITicketService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 购物小票接口
 *
 * @author chengxg
 * @Date 2017-08-04 11:07:42
 */
@Controller
@RequestMapping("/web/ticket")
public class WebTicketController extends BaseController {
	@Autowired
	private ITicketService ticketService;
	
	@Autowired
	IMemberService memberService;
	
	@Autowired
    GunsProperties gunsProperties;
	
	@ApiOperation("获得小票规格")
	@RequestMapping(value="info",method=RequestMethod.POST)
	@ResponseBody
	public String info(@ApiParam(value = "店铺编号", required = true) @RequestParam(required = true) String storeNo,
			@ApiParam(value = "签名", required = true) @RequestParam(required = true) String sign) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			boolean isSign = true;
			if(gunsProperties.isCheckSign()){
				Map<String, String> data = new HashMap<String,String>();
				data.put("storeNo", storeNo);
				data.put("sign", sign);
				isSign = WXPayUtil.isSignatureValid(data, gunsProperties.getSignKey());
				if(isSign){
					result.put("code", "500");
					result.put("msg", "签名失败");
				}
			}
			if(isSign){
				JSONObject obj = new JSONObject();
				Ticket ticket = ticketService.selectByStoreNum(storeNo);
				if(ticket!= null){
					obj.put("tilte", ticket.getTitle());
					obj.put("remark", ticket.getRemark());
					obj.put("specType", ticket.getSpecType());
				}
				result.put("content", obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return JSONObject.toJSONString(result);
	}
}
