package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.dto.CouponFetchDo;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.model.CouponFetch;
import com.ikoori.vip.server.modular.biz.dao.CouponFetchDao;
import com.ikoori.vip.server.modular.biz.service.ICouponFetchService;

/**
 * 领取记录Dao
 *
 * @author chengxg
 * @Date 2017-08-14 16:14:52
 */
@Service
public class CouponFetchServiceImpl implements ICouponFetchService {
	@Autowired
	CouponFetchDao couponFetchDao;
	@Autowired
	CouponFetchMapper couponFetchMapper;
	@Override
	public Integer deleteById(Long id) {
		return couponFetchMapper.deleteById(id);
	}

	@Override
	public Integer updateById(CouponFetch couponFetch) {
		return couponFetchMapper.updateById(couponFetch);
	}

	@Override
	public CouponFetch selectById(Long id) {
		return couponFetchMapper.selectById(id);
	}

	@Override
	public Integer insert(CouponFetch couponFetch) {
		return couponFetchMapper.insert(couponFetch);
	}
	
	@Override
	public List<Map<String, Object>> getCouponFetchList(Page<CouponFetch> page, String name, String orderByField,
			boolean isAsc,Long merchantId) {
		return couponFetchDao.getCouponFetchList(page, name, orderByField, isAsc,merchantId);
	}
	
	@Override
	public List<Map<String, Object>> selectByCondition(Page<CouponFetchDo> page, String name, String orderByField,
			boolean isAsc,Long merchantId) {
		return couponFetchDao.selectByCondition(page, name, orderByField, isAsc,merchantId);
	}
	
	public List<Map<String, Object>> selectByMemberId(Long memberId){
		return couponFetchDao.selectByMemberId(memberId);
	}
}
