package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Coupon;

/**
 * 优惠券Dao
 *
 * @author chengxg
 * @Date 2017-08-04 12:20:55
 */
public interface CouponDao {
   List<Map<String, Object>> getCouponList(@Param("page") Page<Coupon> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
