package com.ikoori.vip.server.api;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.service.MemberService;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Override
	public JSONObject test(String name) {
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		System.out.println(name);
		return obj;
	}

}
