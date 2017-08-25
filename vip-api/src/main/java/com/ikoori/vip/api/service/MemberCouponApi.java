package com.ikoori.vip.api.service;

import java.util.List;
import java.util.Map;

public interface MemberCouponApi {
	public List<Map<String, Object>> getMemberCouponByOpenId(String openId);
	public Object getMemberCouponDetailByCouponId(Long couponId,Long id);
}
