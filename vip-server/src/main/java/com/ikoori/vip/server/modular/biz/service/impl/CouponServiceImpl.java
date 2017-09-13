package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.server.config.properties.GunsProperties;
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
	@Autowired
	GunsProperties gunsProperties;
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
	public List<Map<String, Object>> getCouponList(Long merchantId,Boolean isExpired,Boolean isInvalid,Integer type,Long storeId,Page<Coupon> page, String name, String orderByField,
			boolean isAsc) {
		return couponDao.getCouponList(merchantId,isExpired,isInvalid,type,storeId,page, name, orderByField, isAsc);
	}

	@Override
	public List<Coupon> selectByCondition(Map<String, Object> condition) {
		return couponMapper.selectList(new EntityWrapper<Coupon>().eq("status", 1).eq("merchant_id", condition.get("merchantId")));
	}
	
	public void saveCoupon(Coupon coupon){
		if (coupon.getValue() != null) {
			coupon.setOriginValue(coupon.getValue() * 100);
		}
		if(!coupon.isIsAtLeast()){
			coupon.setAtLeast(null);
			coupon.setOriginAtLeast(null);
			coupon.setUseCondition("不限制");
		}
		if (coupon.getAtLeast() != null) {
			coupon.setOriginAtLeast(coupon.getAtLeast() * 100);
			coupon.setUseCondition("满" + coupon.getAtLeast() + "元使用");
		}
		
		coupon.setStartTime(coupon.getStartAt().getTime());
		coupon.setEndTime(coupon.getEndAt().getTime());
		if(coupon.getId() != null){
			couponMapper.updateById(coupon);
		}else{
			couponMapper.insert(coupon);
			coupon.setUrl(gunsProperties.getCouponUrl() + coupon.getId());
			couponMapper.updateById(coupon);
		}
	}
}
