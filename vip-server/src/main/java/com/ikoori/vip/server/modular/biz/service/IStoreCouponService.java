package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.StoreCoupon;

/**
 * 优惠券可用店铺Service
 *
 * @author chengxg
 * @Date 2017-09-23 14:07:11
 */
public interface IStoreCouponService {
	public Integer deleteById(Long id);
	public Integer updateById(StoreCoupon storeCoupon);
	public StoreCoupon selectById(Long id);
	public Integer insert(StoreCoupon storeCoupon);
	List<Map<String, Object>> getStoreCouponList(Page<StoreCoupon> page, String name,String orderByField, boolean isAsc);
	public void saveStoreCoupon(Long couponId,String storeIds);
	public List<StoreCoupon> getByCouponId(Long couponId);
	
}
