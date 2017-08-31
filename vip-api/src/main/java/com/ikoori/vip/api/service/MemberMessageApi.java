package com.ikoori.vip.api.service;

public interface MemberMessageApi {
	public void send(String mobile,String content);
	public String generateContent(Integer code);
}
