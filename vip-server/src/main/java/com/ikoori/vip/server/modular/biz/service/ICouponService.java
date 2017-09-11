package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Coupon;

/**
 * 优惠券Service
 *
 * @author chengxg
 * @Date 2017-08-04 12:20:55
 */
public interface ICouponService {
	public Integer deleteById(Long id);
	public Integer updateById(Coupon coupon);
	public Coupon selectById(Long id);
	public Integer insert(Coupon coupon);
	List<Map<String, Object>> getCouponList(Long merchantId,Boolean isExpired,Boolean isInvalid,Integer type,Long storeId,Page<Coupon> page, String name,String orderByField, boolean isAsc);
	List<Coupon> selectByCondition(Map<String,Object> condition);
	public void saveCoupon(Coupon coupon);
}
