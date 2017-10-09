package com.ikoori.vip.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.ikoori.vip.api.service.CouponApi;
import com.ikoori.vip.api.service.MemberCardApi;
import com.ikoori.vip.api.service.MemberCouponApi;
import com.ikoori.vip.api.service.MemberInfoApi;
import com.ikoori.vip.api.service.MemberOrderApi;
import com.ikoori.vip.api.service.MemberPointApi;
import com.ikoori.vip.api.service.ShareApi;
import com.ikoori.vip.api.service.SignApi;
import com.ikoori.vip.api.service.StoreApi;

@Configuration
public class DubboProvider extends DubboBaseConfig {
	@Bean
	public ServiceBean<MemberCardApi> memberCardProvider(MemberCardApi cardApi) {
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
	public ServiceBean<MemberCouponApi> memberCouponProvider(MemberCouponApi couponApi) {
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
	public ServiceBean<MemberInfoApi> memberInfoProvider(MemberInfoApi infoApi) {
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
	public ServiceBean<MemberPointApi> memberPointProvider(MemberPointApi pointApi) {
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
	public ServiceBean<MemberOrderApi> memberOrderProvider(MemberOrderApi orderApi) {
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
	public ServiceBean<StoreApi> storeProvider(StoreApi storeApi) {
		ServiceBean<StoreApi> serviceBean = new ServiceBean<StoreApi>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(StoreApi.class.getName());
		serviceBean.setRef(storeApi);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
	
	@Bean
	public ServiceBean<CouponApi> couponProvider(CouponApi couponApi) {
		ServiceBean<CouponApi> serviceBean = new ServiceBean<CouponApi>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(CouponApi.class.getName());
		serviceBean.setRef(couponApi);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
	
	@Bean
	public ServiceBean<SignApi> signProvider(SignApi signApi) {
		ServiceBean<SignApi> serviceBean = new ServiceBean<SignApi>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(SignApi.class.getName());
		serviceBean.setRef(signApi);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
	
	@Bean
	public ServiceBean<ShareApi> shareProvider(ShareApi shareApi) {
		ServiceBean<ShareApi> serviceBean = new ServiceBean<ShareApi>();
		serviceBean.setProxy("javassist");
		serviceBean.setVersion("myversion");
		serviceBean.setInterface(ShareApi.class.getName());
		serviceBean.setRef(shareApi);
		serviceBean.setTimeout(5000);
		serviceBean.setRetries(3);
		return serviceBean;
	}
}
