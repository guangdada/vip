package com.ikoori.vip.api.service;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.vo.UserInfo;

public interface MemberInfoApi {

	public JSONObject getMemberInfoByUnionid(String unionid);
	
	public Object getWxUserByUnionid(String unionid);
	
	public Object getMemberByMobile(String mobile);

	public void saveMemberInfo(UserInfo userInfo,boolean update) throws Exception;

	public int updateMemberInfoByUnionid(String unionid, String mobile, String name, int sex, Date birthday,
			String address, String area);
	
	public int activeMemberByUnionid(String unionid, String mobile,String ip,boolean sendPack);
}
