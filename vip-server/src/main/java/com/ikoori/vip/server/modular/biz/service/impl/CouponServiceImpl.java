package com.ikoori.vip.server.modular.biz.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.util.DateUtil;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.service.ICouponService;

/**
 * 优惠券Dao
 *
 * @author chengxg
 * @Date 2017-08-04 12:20:55
 */
@Service
public class CouponServiceImpl implements ICouponService {
	@Autowired
	CouponDao couponDao;
	@Autowired
	CouponMapper couponMapper;
	@Override
	public Integer deleteById(Long id) {
		return couponMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Coupon coupon) {
		return couponMapper.updateById(coupon);
	}

	@Override
	public Coupon selectById(Long id) {
		return couponMapper.selectById(id);
	}

	@Override
	public Integer insert(Coupon coupon) {
		return couponMapper.insert(coupon);
	}
	
	@Override
	public List<Map<String, Object>> getCouponList(Page<Coupon> page, String name, String orderByField,
			boolean isAsc) {
		return couponDao.getCouponList(page, name, orderByField, isAsc);
	}

	@Override
	public List<Coupon> selectByCondition(Map<String, Object> condition) {
		return couponMapper.selectList(new EntityWrapper<Coupon>().eq("status", 1).eq("merchant_id", condition.get("merchantId")));
	}
	
	public void saveCoupon(Coupon coupon){
		if (coupon.getValue() != null) {
			coupon.setOriginValue(coupon.getValue().multiply(new BigDecimal(100)).intValue());
		}
		if (coupon.getAtLeast() != null) {
			coupon.setOriginAtLeast(coupon.getAtLeast().multiply(new BigDecimal(100)).intValue());
		}
		if (StringUtils.isNotBlank(coupon.getStartAtStr())) {
			Date startD = DateUtil.parseTime(coupon.getStartAtStr());
			coupon.setStartAt(startD);
			coupon.setStartTime(startD.getTime());
		}
		if (StringUtils.isNotBlank(coupon.getEndAtStr())) {
			Date endD = DateUtil.parseTime(coupon.getEndAtStr());
			coupon.setEndAt(endD);
			coupon.setEndTime(endD.getTime());
		}
		if(coupon.getId() != null){
			couponMapper.updateById(coupon);
		}else{
			couponMapper.insert(coupon);
		}
	}
}
