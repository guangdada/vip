package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.ShareLog;

/**
 * 分享日志Service
 *
 * @author chengxg
 * @Date 2017-10-16 14:22:18
 */
public interface IShareLogService {
	public Integer deleteById(Long id);

	public Integer updateById(ShareLog shareLog);

	public ShareLog selectById(Long id);

	public Integer insert(ShareLog shareLog);

	List<Map<String, Object>> getShareLogList(Page<Map<String, Object>> page, String shareName, String receiveName,
			Integer receiveStatus, String mobile, Long merchantId,String orderByField, boolean isAsc);

}
