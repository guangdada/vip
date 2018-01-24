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
import com.ikoori.vip.common.constant.state.SpecType;
import com.ikoori.vip.common.persistence.model.Store;
import com.ikoori.vip.common.persistence.model.Ticket;
import com.ikoori.vip.common.util.WXPayUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.modular.biz.service.IMemberService;
import com.ikoori.vip.server.modular.biz.service.IStoreService;
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
	IStoreService storeService;
	
	@Autowired
    GunsProperties gunsProperties;
	
	
	@ApiOperation("获得小票规格")
	@RequestMapping(value="getTemplate",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTemplate(@ApiParam(value = "店铺编号", required = true) @RequestParam(required = true) String storeNo,
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
				if(!isSign){
					result.put("code", "500");
					result.put("msg", "签名失败");
				}
			}
			if (isSign) {
				Map<String, Object> obj = new HashMap<String, Object>();
				Store store = storeService.selectByStoreNo(storeNo);
				Ticket ticket = ticketService.selectByStoreNum(storeNo);
				if (ticket != null) {
					obj.put("logo",store.getLogo());
					obj.put("title", ticket.getTitle());
					obj.put("remark", ticket.getRemark());
					obj.put("specType", SpecType.valueOf(ticket.getSpecType()).substring(0, 2));
					obj.put("storeName", store.getName());
					obj.put("address", store.getAddress());
					obj.put("servicePhone", store.getServicePhone());
					obj.put("website", store.getWebsite());
					result.put("params", obj);
					result.put("template", ticket.getSetting());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return result;
	}
	
	@ApiOperation("获得小票规格")
	@RequestMapping(value="info",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> info(@ApiParam(value = "店铺编号", required = true) @RequestParam(required = true) String storeNo,
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
				if(!isSign){
					result.put("code", "500");
					result.put("msg", "签名失败");
				}
			}
			if (isSign) {
				JSONObject obj = new JSONObject();
				Store store = storeService.selectByStoreNo(storeNo);
				Ticket ticket = ticketService.selectByStoreNum(storeNo);
				if (ticket != null) {
					JSONObject logo = new JSONObject();
					logo.put("name", "店铺logo");
					logo.put("value", store.getLogo());
					obj.put("logo",logo);
					
					JSONObject qrcode = new JSONObject();
					qrcode.put("name", "公众号二维码");
					qrcode.put("value", store.getQrcode());
					obj.put("qrcode",qrcode);
					
					JSONObject joinTel = new JSONObject();
					joinTel.put("name", "加盟热线");
					joinTel.put("value", store.getJointel());
					obj.put("joinTel",joinTel);
					
					JSONObject title = new JSONObject();
					title.put("name", "标题");
					title.put("value", ticket.getTitle());
					obj.put("title", title);
					
					JSONObject remark = new JSONObject();
					remark.put("name", "备注");
					remark.put("value", ticket.getRemark());
					obj.put("remark", remark);
					
					JSONObject specType = new JSONObject();
					specType.put("name", "规格");
					specType.put("value", SpecType.valueOf(ticket.getSpecType()));
					obj.put("specType", specType);
					
					JSONObject storeName = new JSONObject();
					storeName.put("name", "店铺名称");
					storeName.put("value", store.getName());
					obj.put("storeName", storeName);
					
					
					JSONObject address = new JSONObject();
					address.put("name", "地址");
					address.put("value", store.getAddress());
					obj.put("address", address);
					
					
					JSONObject servicePhone = new JSONObject();
					servicePhone.put("name", "服务电话");
					servicePhone.put("value", store.getServicePhone());
					obj.put("servicePhone", servicePhone);
					
					JSONObject website = new JSONObject();
					website.put("name", "网址");
					website.put("value", store.getWebsite());
					obj.put("website", website);
				}
				result.put("content", obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return result;
	}
}
