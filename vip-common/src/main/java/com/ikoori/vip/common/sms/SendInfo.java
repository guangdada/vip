package com.ikoori.vip.common.sms;


public class SendInfo {
	String moblie;
	String content;
	public String getMoblie() {
		return moblie;
	}
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public SendInfo(String moblie, String content) {
		super();
		this.moblie = moblie;
		this.content = content;
	}
	public SendInfo() {
		super();
	}

}
