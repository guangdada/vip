package com.ikoori.vip.mobile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.ikoori.vip.api.service.MemberService;

@Configuration
public class Consumer extends DubboBaseConfig {
	@Bean
	public ReferenceBean<MemberService> personConsumer() {
		ReferenceBean<MemberService> ref = new ReferenceBean<MemberService>();
		ref.setVersion("myversion");
		ref.setInterface(MemberService.class);
		ref.setTimeout(5000);
		ref.setRetries(3);
		ref.setCheck(false);
		return ref;
	}
}
