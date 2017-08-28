package com.ikoori.vip.api.service;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
public interface MemberOrderApi {
	public List<Map<String, Object>> getMemberOrderByOpenId(String openId);
	public List<Map<String, Object>> getMemberOrderDetailByOrderId(Long orderId);
}
