package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.PointTradeMapper;
import com.ikoori.vip.common.persistence.model.PointTrade;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
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
	@Autowired
	MemberDao memberDao;

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
			Integer inOut, Long pointId, Long merchantId, Integer tradeType, String orderByField, boolean isAsc) {
		return pointTradeDao.getPointTradeList(page, nickname, mobile, inOut, pointId, merchantId, tradeType,
				orderByField, isAsc);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean savePointTrade(Boolean inout, Integer tradeType, Integer points, Long memberId, Long pointId,
			Long merchantId, Long storeId,String tag) {
		PointTrade pointTrade = new PointTrade();
		pointTrade.setInOut(inout);
		pointTrade.setTradeType(tradeType);
		pointTrade.setPoint(points);
		pointTrade.setMemberId(memberId);
		pointTrade.setPointId(pointId);
		pointTrade.setMerchantId(merchantId);
		pointTrade.setTag(tag);
		pointTradeMapper.insert(pointTrade);
		return memberDao.updatePoint(memberId, points) == 0 ? false : true;
	}
}
