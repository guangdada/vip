package com.ikoori.vip.api.service;

import java.util.List;
import java.util.Map;
public interface MemberOrderApi {
	public List<Map<String, Object>> getMemberOrderByUnionid(String unionid,int start,int pageSize);
	public List<Map<String, Object>> getMemberOrderDetailByOrderId(Long orderId);
}
