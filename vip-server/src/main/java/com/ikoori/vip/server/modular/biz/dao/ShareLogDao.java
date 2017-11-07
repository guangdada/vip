package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 分享日志Dao
 *
 * @author chengxg
 * @Date 2017-10-16 14:22:18
 */
public interface ShareLogDao {
	List<Map<String, Object>> getShareLogList(@Param("page") Page<Map<String, Object>> page,
			@Param("shareName") String shareName, @Param("receiveName") String receiveName,
			@Param("receiveStatus") Integer receiveStatus, @Param("mobile") String mobile,
			@Param("merchantId") Long merchantId, @Param("orderByField") String orderByField,
			@Param("isAsc") boolean isAsc);

	Integer getShareCount(@Param("shareUnionid") String shareUnionid,@Param("shareDate") String shareDate);

}
