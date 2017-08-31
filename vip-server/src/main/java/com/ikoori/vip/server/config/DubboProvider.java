package com.ikoori.vip.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.ikoori.vip.api.service.MemberCardApi;
import com.ikoori.vip.api.service.MemberCouponApi;
import com.ikoori.vip.api.service.MemberInfoApi;
import com.ikoori.vip.api.service.MemberMessageApi;
import com.ikoori.vip.api.service.MemberOrderApi;
import com.ikoori.vip.api.service.MemberPointApi;
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
	@Bean
	public ServiceBean<MemberCardApi> cardProvider(MemberCardApi cardApi) {
		ServiceBean<MemberCardApi> serviceBean = new ServiceBean<MemberCardApi>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(MemberCardApi.class.getName());
		serviceBean.setRef(cardApi);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
	@Bean
	public ServiceBean<MemberCouponApi> couponProvider(MemberCouponApi couponApi) {
		ServiceBean<MemberCouponApi> serviceBean = new ServiceBean<MemberCouponApi>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(MemberCouponApi.class.getName());
		serviceBean.setRef(couponApi);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
	@Bean
	public ServiceBean<MemberInfoApi> infoProvider(MemberInfoApi infoApi) {
		ServiceBean<MemberInfoApi> serviceBean = new ServiceBean<MemberInfoApi>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(MemberInfoApi.class.getName());
		serviceBean.setRef(infoApi);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
	
	@Bean
	public ServiceBean<MemberPointApi> pointProvider(MemberPointApi pointApi) {
		ServiceBean<MemberPointApi> serviceBean = new ServiceBean<MemberPointApi>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(MemberPointApi.class.getName());
		serviceBean.setRef(pointApi);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
	@Bean
	public ServiceBean<MemberOrderApi> orderProvider(MemberOrderApi orderApi) {
		ServiceBean<MemberOrderApi> serviceBean = new ServiceBean<MemberOrderApi>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(MemberOrderApi.class.getName());
		serviceBean.setRef(orderApi);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
	@Bean
	public ServiceBean<MemberMessageApi> messageProvider(MemberMessageApi messageApi) {
		ServiceBean<MemberMessageApi> serviceBean = new ServiceBean<MemberMessageApi>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(MemberMessageApi.class.getName());
		serviceBean.setRef(messageApi);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
}
