package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 红包Dao
 *
 * @author chengxg
 * @Date 2017-10-19 11:30:26
 */
public interface RedpackDao {
	List<Map<String, Object>> getRedpackList(@Param("page") Page<Map<String, Object>> page, @Param("name") String name,
			@Param("packType") Integer packType, @Param("sendType") Integer sendType,
			@Param("merchantId") Long merchantId, @Param("orderByField") String orderByField,
			@Param("isAsc") boolean isAsc);

	public int updateSendInfo(@Param("sendAmount") Integer sendAmount, @Param("id") Long id);

	public int updateReciveInfo(@Param("receiveAmount") Integer receiveAmount, @Param("id") Long id);
}
