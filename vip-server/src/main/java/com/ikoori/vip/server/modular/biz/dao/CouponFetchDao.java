package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.dto.CouponFetchDo;
import com.ikoori.vip.common.persistence.model.CouponFetch;

/**
 * 领取记录Dao
 *
 * @author chengxg
 * @Date 2017-08-14 16:14:52
 */
public interface CouponFetchDao {
   List<Map<String, Object>> getCouponFetchList(@Param("page") Page<CouponFetch> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,@Param("merchantId") Long merchantId);
   
   List<Map<String, Object>> selectByCondition(@Param("nickname") String nickname,@Param("type") Integer type ,@Param("mobile") String mobile ,@Param("isUsed") Integer isUsed,@Param("page") Page<Object> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,@Param("merchantId") Long merchantId);
   
   public List<Map<String, Object>> selectByMemberId(@Param("memberId") Long memberId);
   
   public CouponFetch selectByVerifyCode(@Param("verifyCode") String verifyCode);
	 /*
	可用优惠券（未使用、未失效）
	失效优惠券（使用、失效）
	*/
   List<Map<String, Object>> selectCoupon(@Param("openId") String openId);
   /*
  	优惠券详情
  	*/
   Object selectCouponDetail(@Param("couponId") Long couponId,@Param("id") Long id);
}
