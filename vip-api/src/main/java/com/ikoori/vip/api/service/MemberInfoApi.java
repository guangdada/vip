package com.ikoori.vip.api.service;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.vo.UserInfo;

public interface MemberInfoApi {

	public JSONObject getMemberInfoByOpenId(String openId);

	public Object getMemberByMobile(String mobile);

	public void saveMemberInfo(UserInfo userInfo) throws Exception;

	public int updateMemberInfoByOpenId(String openId, String mobile, String name, int sex, Date birthday,
			String address, String area);
}
