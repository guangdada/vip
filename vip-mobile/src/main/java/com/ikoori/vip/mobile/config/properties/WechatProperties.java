package com.ikoori.vip.mobile.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 基础配置
 * 
 * @ClassName: GunsProperties
 * @author: chengxg
 * @date: 2017年10月31日 下午5:15:07
 */
@Configuration
@ConfigurationProperties(prefix = WechatProperties.PREFIX)
public class WechatProperties {

	public static final String PREFIX = "wechat";
	// 微信端地址
	private String xcxAppid;
	private String xcxSecret;
	private String appid;
	private String secret;

	public String getXcxAppid() {
		return xcxAppid;
	}

	public void setXcxAppid(String xcxAppid) {
		this.xcxAppid = xcxAppid;
	}

	public String getXcxSecret() {
		return xcxSecret;
	}

	public void setXcxSecret(String xcxSecret) {
		this.xcxSecret = xcxSecret;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
