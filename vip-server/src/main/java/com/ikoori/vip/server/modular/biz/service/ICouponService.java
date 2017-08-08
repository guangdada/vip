package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
	List<Map<String, Object>> getCouponList(@Param("page") Page<Coupon> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
	List<Coupon> selectByCondition(Map<String,Object> condition);
	public void saveCoupon(Coupon coupon);
}
