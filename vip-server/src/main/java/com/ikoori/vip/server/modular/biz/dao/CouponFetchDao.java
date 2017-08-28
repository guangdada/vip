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
   
   List<Map<String, Object>> selectByCondition(@Param("page") Page<CouponFetchDo> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,@Param("merchantId") Long merchantId);
   
   public List<Map<String, Object>> selectByMemberId(@Param("memberId") Long memberId);
   
   public CouponFetch selectByVerifyCode(@Param("verifyCode") String verifyCode);
}
