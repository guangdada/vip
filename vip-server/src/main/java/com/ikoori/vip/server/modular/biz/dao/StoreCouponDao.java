package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.StoreCoupon;

/**
 * 优惠券可用店铺Dao
 *
 * @author chengxg
 * @Date 2017-09-23 14:07:11
 */
public interface StoreCouponDao {
	List<Map<String, Object>> getStoreCouponList(@Param("page") Page<StoreCoupon> page, @Param("name") String name,
			@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
	
	/**
	 * 删除优惠券可用店铺
	 * @Title: deleteByCouponId   
	 * @param couponId
	 * @return
	 * @date:   2017年9月23日 下午2:46:05 
	 * @author: chengxg
	 */
	public int deleteByCouponId(@Param("couponId") Long couponId);
	
	/**
	 * 获得优惠券可用店铺
	 * @Title: getByCouponId   
	 * @param couponId
	 * @return
	 * @date:   2017年9月23日 下午2:19:36 
	 * @author: chengxg
	 */
	public List<StoreCoupon> getByCouponId(@Param("couponId") Long couponId);
}
