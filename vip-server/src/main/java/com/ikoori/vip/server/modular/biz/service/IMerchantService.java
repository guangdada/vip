package com.ikoori.vip.server.modular.biz.service;

import org.springframework.cache.annotation.Cacheable;

import com.ikoori.vip.common.constant.cache.Cache;
import com.ikoori.vip.common.constant.cache.CacheKey;
import com.ikoori.vip.common.persistence.model.Merchant;

/**
 * 商户Service
 *
 * @author fengshuonan
 * @Date 2017-07-28 13:09:10
 */
public interface IMerchantService {
	public void saveMerchant(Merchant merchant);
	
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_MERCHANT_USER + "'+#userId")
	public Merchant getMerchantUserId(Long userId);
}
