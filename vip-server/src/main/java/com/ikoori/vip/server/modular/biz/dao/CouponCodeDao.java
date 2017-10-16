package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;



/**
 * 券码管理Dao
 *
 * @author chengxg
 * @Date 2017-10-13 15:00:23
 */
public interface CouponCodeDao {
	List<Map<String, Object>> getCouponCodeList(@Param("page") Page<Map<String, Object>> page,
			@Param("batchNo") String batchNo,
			@Param("verifyCode") String verifyCode, @Param("verifyNo") String verifyNo,
			@Param("useStatus") Integer useStatus, @Param("couponId") Long couponId,
			@Param("merchantId") Long merchantId, @Param("orderByField") String orderByField,
			@Param("isAsc") boolean isAsc);
	
	public void updateByBatchNo(String batchNo, Long merchantId,Integer useStatus);
	
	public void updateUseStatus(Map<String, Object> params);
	
}
