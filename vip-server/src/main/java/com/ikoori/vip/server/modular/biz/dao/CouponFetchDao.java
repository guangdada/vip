package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.CouponFetch;

/**
 * 领取记录Dao
 *
 * @author chengxg
 * @Date 2017-08-14 16:14:52
 */
public interface CouponFetchDao {
	List<Map<String, Object>> getCouponFetchList(@Param("page") Page<CouponFetch> page, @Param("name") String name,
			@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,
			@Param("merchantId") Long merchantId);

	List<Map<String, Object>> selectByCondition(@Param("verifyCode") String verifyCode,
			@Param("nickname") String nickname, @Param("type") Integer type, @Param("mobile") String mobile,
			@Param("isUsed") Integer isUsed, @Param("page") Page<Object> page, @Param("name") String name,
			@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,
			@Param("merchantId") Long merchantId);

	/**
	 * 根据手机号和店铺编号查询会员还在有效期内的券
	 * @Title: selectByMemberId   
	 * @param memberId
	 * @param storeNo
	 * @return
	 * @date:   2017年9月23日 下午3:56:56 
	 * @author: chengxg
	 */
	public List<Map<String, Object>> selectByMemberId(@Param("memberId") Long memberId,@Param("storeNo") String storeNo);

	/**
	 * 根据券号获得优惠券,关联查询coupon
	 * @Title: selectByVerifyCodeJoinCoupon   
	 * @param verifyCode
	 * @return
	 * @date:   2017年9月18日 下午4:27:04 
	 * @author: chengxg
	 */
	public CouponFetch selectByVerifyCodeJoinCoupon(@Param("verifyCode") String verifyCode);
	
	/**
	 * 根据券号获得优惠券
	 * @Title: selectByVerifyCode   
	 * @param verifyCode
	 * @return
	 * @date:   2017年9月18日 下午11:08:07 
	 * @author: chengxg
	 */
	public CouponFetch selectByVerifyCode(@Param("verifyCode") String verifyCode);

	/*
	 * 可用优惠券（未使用、未失效） 失效优惠券（使用、失效）
	 */
	List<Map<String, Object>> selectCoupon(@Param("openId") String openId);

	/*
	 * 优惠券详情
	 */
	Object selectCouponDetail(@Param("couponId") Long couponId, @Param("id") Long id);
}
