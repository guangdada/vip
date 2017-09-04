package com.ikoori.vip.api.service;



import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public interface MemberInfoApi {
	
	public JSONObject getMemberInfoByOpenId(String openId);
	public Object getMemberByMobile(String mobile);
	public int updateMemberInofByOpenId(String openId,String mobile,String name,int sex,Date birthday,String address);
}
