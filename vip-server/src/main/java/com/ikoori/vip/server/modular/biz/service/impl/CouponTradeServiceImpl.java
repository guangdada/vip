package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.CouponTradeMapper;
import com.ikoori.vip.common.persistence.model.CouponTrade;
import com.ikoori.vip.server.modular.biz.dao.CouponTradeDao;
import com.ikoori.vip.server.modular.biz.service.ICouponTradeService;

/**
 * 使用记录Dao
 *
 * @author chengxg
 * @Date 2017-08-14 16:15:04
 */
@Service
public class CouponTradeServiceImpl implements ICouponTradeService {
	@Autowired
	CouponTradeDao couponTradeDao;
	@Autowired
	CouponTradeMapper couponTradeMapper;
	@Override
	public Integer deleteById(Long id) {
		return couponTradeMapper.deleteById(id);
	}

	@Override
	public Integer updateById(CouponTrade couponTrade) {
		return couponTradeMapper.updateById(couponTrade);
	}

	@Override
	public CouponTrade selectById(Long id) {
		return couponTradeMapper.selectById(id);
	}

	@Override
	public Integer insert(CouponTrade couponTrade) {
		return couponTradeMapper.insert(couponTrade);
	}
	
	@Override
	public List<Map<String, Object>> getCouponTradeList(Page<CouponTrade> page, String name, String orderByField,
			boolean isAsc,Long merchantId) {
		return couponTradeDao.getCouponTradeList(page, name, orderByField, isAsc,merchantId);
	}
	
	@Override
	public List<Map<String, Object>> selectByCondition(String nickname,Integer type ,String mobile,Page<Object> page, String name, String orderByField,
			boolean isAsc,Long merchantId) {
		return couponTradeDao.selectByCondition(nickname,type,mobile,page, name, orderByField, isAsc,merchantId);
	}
}
