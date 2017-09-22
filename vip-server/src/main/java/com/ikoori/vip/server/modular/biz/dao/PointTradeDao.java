package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 积分明细Dao
 *
 * @author chengxg
 * @Date 2017-09-20 16:36:35
 */
public interface PointTradeDao {
	/**
	 * 分页查询
	 * @Title: getPointTradeList   
	 * @param page
	 * @param nickname
	 * @param mobile
	 * @param inOut
	 * @param pointId
	 * @param merchantId
	 * @param tradeType
	 * @param orderByField
	 * @param isAsc
	 * @return
	 * @date:   2017年9月22日 上午11:44:53 
	 * @author: chengxg
	 */
	List<Map<String, Object>> getPointTradeList(@Param("page") Page<Map<String, Object>> page,
			@Param("nickname") String nickname, @Param("mobile") String mobile, @Param("inOut") Integer inOut,
			@Param("pointId") Long pointId, @Param("merchantId") Long merchantId,@Param("tradeType") Integer tradeType,
			@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
