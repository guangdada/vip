package com.ikoori.vip.server.api;

import org.springframework.stereotype.Service;

import com.ikoori.vip.api.service.MemberMessageApi;
import com.ikoori.vip.common.message.SmsClientQueryCall;
import com.ikoori.vip.common.message.SmsClientQueryStatusReport;
import com.ikoori.vip.common.message.SmsClientSend;

@Service
public class MemberMessageApiImpl implements MemberMessageApi {
	public static String url = "http://sdk.entinfo.cn:8060/webservice.asmx";
	public static String account = "c@e89b@4";
	public static String password = "c@e89b@4";
	public static String mobile = "15674911643";
	public static String content = "验证码：888888 【优易网】";
	public static String extno = "SDK-BBX-010-23871";
	@Override
	public void send(String mobile, String content) {
		String send = SmsClientSend.sms(url, account, password, mobile, content, extno);
		System.out.println(send);
		
	}
	@Override
	public String generateContent(Integer code){
		System.out.println("code:"+code);
		return "您的验证码是："+code;
	}
	public static void query() {

		String query = SmsClientQueryCall.queryStatusReport(url, account,
				password);
		System.out.println(query);
	}

	public static void report() {

		String report = SmsClientQueryStatusReport.queryStatusReport(url, account,
				password);
		System.out.println(report);
	}

}
