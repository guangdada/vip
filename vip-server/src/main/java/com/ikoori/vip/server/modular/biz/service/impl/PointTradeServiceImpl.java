package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.PointTradeMapper;
import com.ikoori.vip.common.persistence.model.PointTrade;
import com.ikoori.vip.server.modular.biz.dao.PointTradeDao;
import com.ikoori.vip.server.modular.biz.service.IPointTradeService;

/**
 * 积分明细Dao
 *
 * @author chengxg
 * @Date 2017-09-20 16:36:35
 */
@Service
public class PointTradeServiceImpl implements IPointTradeService {
	@Autowired
	PointTradeDao pointTradeDao;
	@Autowired
	PointTradeMapper pointTradeMapper;

	@Override
	public Integer deleteById(Long id) {
		return pointTradeMapper.deleteById(id);
	}

	@Override
	public Integer updateById(PointTrade pointTrade) {
		return pointTradeMapper.updateById(pointTrade);
	}

	@Override
	public PointTrade selectById(Long id) {
		return pointTradeMapper.selectById(id);
	}

	@Override
	public Integer insert(PointTrade pointTrade) {
		return pointTradeMapper.insert(pointTrade);
	}

	@Override
	public List<Map<String, Object>> getPointTradeList(Page<Map<String, Object>> page, String nickname, String mobile,
			Integer inOut, Long pointId,Long merchantId, String orderByField, boolean isAsc) {
		return pointTradeDao.getPointTradeList(page, nickname, mobile, inOut, pointId,merchantId, orderByField, isAsc);
	}
}
