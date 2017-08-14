package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.CouponTrade;

/**
 * 使用记录Dao
 *
 * @author chengxg
 * @Date 2017-08-14 16:15:04
 */
public interface CouponTradeDao {
   List<Map<String, Object>> getCouponTradeList(@Param("page") Page<CouponTrade> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc, @Param("merchantId") Long merchantId);
   
   List<Map<String, Object>> selectByCondition(@Param("page") Page<Object> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc, @Param("merchantId") Long merchantId);
}
