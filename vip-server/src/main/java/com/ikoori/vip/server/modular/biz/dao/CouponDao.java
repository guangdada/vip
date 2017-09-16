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
	/**
	 * 分页查询
	 * @Title: getCouponList   
	 * @param merchantId
	 * @param isExpired
	 * @param isInvalid
	 * @param type
	 * @param storeId
	 * @param page
	 * @param name
	 * @param orderByField
	 * @param isAsc
	 * @return
	 * @date:   2017年9月14日 下午3:52:36 
	 * @author: chengxg
	 */
	List<Map<String, Object>> getCouponList(@Param("merchantId") Long merchantId, @Param("isExpired") Boolean isExpired,
			@Param("isInvalid") Boolean isInvalid, @Param("type") Integer type, @Param("storeId") Long storeId,
			@Param("page") Page<Coupon> page, @Param("name") String name, @Param("orderByField") String orderByField,
			@Param("isAsc") boolean isAsc);
	
	/**
	 * 修改库存数量
	 * @Title: updateStock   
	 * @param couponId
	 * @return
	 * @date:   2017年9月14日 下午3:53:49 
	 * @author: chengxg
	 */
	int updateStock(@Param("couponId") Long couponId);
	
	/**
	 * 批量修改库存
	 * @Title: updateStock   
	 * @param couponId
	 * @param getCount
	 * @return
	 * @date:   2017年9月15日 下午8:00:40 
	 * @author: chengxg
	 */
	int updateStock(@Param("couponId") Long couponId,@Param("getCount") Integer getCount);
	
	/**
	 * 更新优惠券领取人数
	 * @Title: updateGetCountUser   
	 * @param couponId
	 * @param memberId
	 * @return
	 * @date:   2017年9月15日 上午10:15:55 
	 * @author: chengxg
	 */
	int updateGetCountUser(@Param("couponId") Long couponId,@Param("memberId") Long memberId);
	
	/**
	 * 根据alias获得优惠券
	 * @Title: getCouponByAlias   
	 * @param alias
	 * @return
	 * @date:   2017年9月15日 上午10:27:06 
	 * @author: chengxg
	 */
	Coupon getCouponByAlias(@Param("alias") String alias);
}
