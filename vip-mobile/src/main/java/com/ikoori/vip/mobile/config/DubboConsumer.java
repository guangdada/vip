package com.ikoori.vip.mobile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.ikoori.vip.api.service.CouponApi;
import com.ikoori.vip.api.service.MemberCardApi;
import com.ikoori.vip.api.service.MemberCouponApi;
import com.ikoori.vip.api.service.MemberInfoApi;
import com.ikoori.vip.api.service.MemberOrderApi;
import com.ikoori.vip.api.service.MemberPointApi;
import com.ikoori.vip.api.service.StoreApi;

@Configuration
public class DubboConsumer extends DubboBaseConfig {
	@Bean
	public ReferenceBean<MemberCardApi> getMemberCardApi() {
		ReferenceBean<MemberCardApi> ref = new ReferenceBean<MemberCardApi>();
		ref.setVersion("myversion");
		ref.setInterface(MemberCardApi.class);
		ref.setTimeout(5000);
		ref.setRetries(3);
		ref.setCheck(false);
		return ref;
	}
	@Bean
	public ReferenceBean<MemberCouponApi> getMemberCouponApi() {
		ReferenceBean<MemberCouponApi> ref = new ReferenceBean<MemberCouponApi>();
		ref.setVersion("myversion");
		ref.setInterface(MemberCouponApi.class);
		ref.setTimeout(5000);
		ref.setRetries(3);
		ref.setCheck(false);
		return ref;
	}
	@Bean
	public ReferenceBean<MemberInfoApi> getMemberInfoApi() {
		ReferenceBean<MemberInfoApi> ref = new ReferenceBean<MemberInfoApi>();
		ref.setVersion("myversion");
		ref.setInterface(MemberInfoApi.class);
		ref.setTimeout(5000);
		ref.setRetries(3);
		ref.setCheck(false);
		return ref;
	}
	@Bean
	public ReferenceBean<MemberPointApi> getMemberPointApi() {
		ReferenceBean<MemberPointApi> ref = new ReferenceBean<MemberPointApi>();
		ref.setVersion("myversion");
		ref.setInterface(MemberPointApi.class);
		ref.setTimeout(5000);
		ref.setRetries(3);
		ref.setCheck(false);
		return ref;
	}
	@Bean
	public ReferenceBean<MemberOrderApi> getMemberOrderApi() {
		ReferenceBean<MemberOrderApi> ref = new ReferenceBean<MemberOrderApi>();
		ref.setVersion("myversion");
		ref.setInterface(MemberOrderApi.class);
		ref.setTimeout(5000);
		ref.setRetries(3);
		ref.setCheck(false);
		return ref;
	}
	@Bean
	public ReferenceBean<StoreApi> getStoreApi() {
		ReferenceBean<StoreApi> ref = new ReferenceBean<StoreApi>();
		ref.setVersion("myversion");
		ref.setInterface(StoreApi.class);
		ref.setTimeout(5000);
		ref.setRetries(3);
		ref.setCheck(false);
		return ref;
	}
	
	@Bean
	public ReferenceBean<CouponApi> getCouponApi() {
		ReferenceBean<CouponApi> ref = new ReferenceBean<CouponApi>();
		ref.setVersion("myversion");
		ref.setInterface(CouponApi.class);
		ref.setTimeout(5000);
		ref.setRetries(3);
		ref.setCheck(false);
		return ref;
	}
}
