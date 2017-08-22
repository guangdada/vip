package com.ikoori.vip.api.service;

import com.alibaba.fastjson.JSONObject;

public interface MemberCardApi {
	public JSONObject getMemberCardByOpenId(String openId);
}
