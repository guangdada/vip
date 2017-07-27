package com.ikoori.vip.server.api;

import org.springframework.stereotype.Service;

import com.ikoori.vip.api.service.MemberService;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Override
	public void test() {
		System.out.println("1111");
	}

}
