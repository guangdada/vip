package com.ikoori.vip.mobile.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 基础配置
 * @ClassName:  GunsProperties
 * @author: chengxg
 * @date:   2017年10月31日 下午5:15:07
 */
@Configuration
@ConfigurationProperties(prefix = GunsProperties.PREFIX)
public class GunsProperties {

	public static final String PREFIX = "guns";
	// 微信端地址
	private String clientUrl;

	public String getClientUrl() {
		return clientUrl;
	}

	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}
}
