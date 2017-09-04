package com.ikoori.vip.server.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.service.MemberInfoApi;
import com.ikoori.vip.common.persistence.dao.CardMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.MerchantMapper;
import com.ikoori.vip.common.persistence.dao.WxUserMapper;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.util.DateUtil;
import com.ikoori.vip.server.modular.biz.dao.CouponFetchDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;


@Service
public class MemberInfoApiImpl implements MemberInfoApi {
	@Autowired
	MemberCardMapper memberCardMapper;
	@Autowired
	MemberDao memberDao;
	@Autowired
	WxUserMapper wxUserMapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	CardMapper cardMapper;
	@Autowired
	MerchantMapper merchantMapper;
	@Autowired
	CouponFetchDao couponFetchDao;

	@Override
	public JSONObject getMemberInfoByOpenId(String openId) {
		Member member = memberDao.getMemberByOpenId(openId);
		if(member == null){
			return null;
		}
		JSONObject obj = new JSONObject();
		obj.put("id", member.getId());
		obj.put("name", member.getName());
		obj.put("sex", member.getSex());
		obj.put("address", member.getAddress());
		//obj.put("birthday", DateUtil.getTime(member.getBirthday()));
		obj.put("birthday", member.getBirthday());
		obj.put("mobile", member.getMobile());
		obj.put("isActive",member.isIsActive());
		return obj;
	}

	@Override
	public int updateMemberInofByOpenId(String openId, String mobile, String name, int sex, Date birthday,
			String address) {
		return memberDao.updateMemberInfoByOpenId(openId, name, mobile, sex, address, birthday);
	}

	@Override
	public Object getMemberByMobile(String mobile) {
		// TODO Auto-generated method stub
		Member member=memberDao.getMemberByMobile(mobile);
		if(member==null){
			return null;
		}
		return member;
	}
}
