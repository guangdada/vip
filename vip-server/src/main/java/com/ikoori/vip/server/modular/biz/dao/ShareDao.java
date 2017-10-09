package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Share;

/**
 * 分享规则Dao
 *
 * @author chengxg
 * @Date 2017-10-16 14:22:06
 */
public interface ShareDao {
	List<Map<String, Object>> getShareList(@Param("page") Page<Map<String, Object>> page,
			@Param("merchantId") Long merchantId, @Param("orderByField") String orderByField,
			@Param("isAsc") boolean isAsc);
}
