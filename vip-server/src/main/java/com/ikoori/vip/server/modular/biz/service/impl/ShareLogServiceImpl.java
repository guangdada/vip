package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.ShareLogMapper;
import com.ikoori.vip.common.persistence.model.ShareLog;
import com.ikoori.vip.server.modular.biz.dao.ShareLogDao;
import com.ikoori.vip.server.modular.biz.service.IShareLogService;

/**
 * 分享日志Dao
 *
 * @author chengxg
 * @Date 2017-10-16 14:22:18
 */
@Service
public class ShareLogServiceImpl implements IShareLogService {
	@Autowired
	ShareLogDao shareLogDao;
	@Autowired
	ShareLogMapper shareLogMapper;

	@Override
	public Integer deleteById(Long id) {
		return shareLogMapper.deleteById(id);
	}

	@Override
	public Integer updateById(ShareLog shareLog) {
		return shareLogMapper.updateById(shareLog);
	}

	@Override
	public ShareLog selectById(Long id) {
		return shareLogMapper.selectById(id);
	}

	@Override
	public Integer insert(ShareLog shareLog) {
		return shareLogMapper.insert(shareLog);
	}

	@Override
	public List<Map<String, Object>> getShareLogList(Page<Map<String, Object>> page, String shareName, String receiveName,
			Integer receiveStatus, String mobile,Long merchantId, String orderByField, boolean isAsc) {
		return shareLogDao.getShareLogList(page, shareName, receiveName, receiveStatus, mobile,merchantId, orderByField, isAsc);
	}
}
