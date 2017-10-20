package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 红包记录Dao
 *
 * @author chengxg
 * @Date 2017-10-19 15:52:13
 */
public interface RedpackLogDao {
	List<Map<String, Object>> getRedpackLogList(@Param("page") Page<Map<String, Object>> page,
			@Param("merchantId") Long merchantId, @Param("billno") String billno, @Param("openid") String openid,
			@Param("sendStatus") Integer sendStatus, @Param("sendS") String sendS, @Param("sendE") String sendE,
			@Param("redpackId") Integer redpackId, @Param("orderByField") String orderByField,
			@Param("isAsc") boolean isAsc);
}
