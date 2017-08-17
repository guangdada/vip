package com.ikoori.vip.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.ikoori.vip.api.service.MemberService;

@Configuration
public class DubboProvider extends DubboBaseConfig {
	@Bean
	public ServiceBean<MemberService> personProvider(MemberService memberService) {
		ServiceBean<MemberService> serviceBean = new ServiceBean<MemberService>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(MemberService.class.getName());
		serviceBean.setRef(memberService);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
}
