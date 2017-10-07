package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Sign;

/**
 * 签到规则Dao
 *
 * @author chengxg
 * @Date 2017-09-28 21:50:21
 */
public interface SignDao {
	List<Map<String, Object>> getSignList(@Param("page") Page<Sign> page, @Param("name") String name,
			@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,
			@Param("merchantId") Long merchantId);
}
