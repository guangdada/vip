package com.ikoori.vip.server.modular.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.Ticket;
import com.ikoori.vip.server.common.controller.BaseController;
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
	
	@ApiOperation("获得小票规格")
	@RequestMapping(value="info",method=RequestMethod.POST)
	public Object info(@ApiParam(value = "店铺编号", required = true) String storeNum,
			@ApiParam(value = "签名", required = true) String sign) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			JSONObject obj = new JSONObject();
			Ticket ticket = ticketService.selectByStoreNum(storeNum);
			obj.put("tilte", ticket.getTitle());
			obj.put("remark", ticket.getRemark());
			obj.put("specType", ticket.getSpecType());
			result.put("content", obj);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return JSONObject.toJSONString(result);
	}
}
