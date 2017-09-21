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
	List<Map<String, Object>> getPointTradeList(@Param("page") Page<Map<String, Object>> page,
			@Param("nickname") String nickname, @Param("mobile") String mobile, @Param("inOut") Integer inOut,
			@Param("pointId") Long pointId, @Param("merchantId") Long merchantId,
			@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
