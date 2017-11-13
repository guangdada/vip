package com.ikoori.vip.api.service;

import java.util.List;
import java.util.Map;

public interface MemberPointApi {
	
	public List<Map<String,Object>> getMemberPointByUnionid(String unionid,int start,int pageSize);
}
