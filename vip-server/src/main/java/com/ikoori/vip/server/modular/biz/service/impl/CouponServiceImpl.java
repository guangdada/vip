package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.service.ICouponService;
import com.ikoori.vip.server.modular.biz.service.IStoreCouponService;

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
	@Autowired
	IStoreCouponService storeCouponService;
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
	
	/**
	 * 分页查询
	 * @Title: getCouponList   
	 * @param merchantId
	 * @param isExpired
	 * @param isInvalid
	 * @param type
	 * @param storeId
	 * @param page
	 * @param name
	 * @param orderByField
	 * @param isAsc
	 * @return
	 * @date:   2017年9月18日 下午2:38:29 
	 * @author: chengxg
	 */
	@Override
	public List<Map<String, Object>> getCouponList(Long merchantId, Boolean isExpired, Boolean isInvalid, Integer type,
			Long storeId, Page<Coupon> page, String name, String orderByField, boolean isAsc) {
		return couponDao.getCouponList(merchantId, isExpired, isInvalid, type, storeId, page, name, orderByField,
				isAsc);
	}

	/**
	 * 查询优惠券
	 * @Title: selectByCondition   
	 * @param condition
	 * @return
	 * @date:   2017年9月18日 下午2:35:17 
	 * @author: chengxg
	 */
	@Override
	public List<Coupon> selectByCondition(Map<String, Object> condition) {
		return couponMapper.selectList(new EntityWrapper<Coupon>().eq("status", 1).eq("is_invalid", 1).eq("merchant_id",
				condition.get("merchantId")));
	}
	
	/**
	 * 优惠券保存
	 * @Title: saveCoupon   
	 * @param coupon
	 * @date:   2017年9月18日 下午2:34:36 
	 * @author: chengxg
	 */
	@Transactional(readOnly = false)
	public void saveCoupon(Coupon coupon,String storeIds){
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
		coupon.setLimitStore(StringUtils.isBlank(storeIds) ? false : true);
		if(coupon.getId() != null){
			Coupon couponDb = couponMapper.selectById(coupon.getId());
			if (coupon.getTotal() < couponDb.getGetCount()) {
				throw new BussinessException(500, "发放总量不能小于领取数量" + couponDb.getGetCount());
			}
			int stock = coupon.getTotal() - couponDb.getTotal();
			couponDb.setStock(couponDb.getStock() + stock);
			couponDb.setName(coupon.getName());
			couponDb.setTotal(coupon.getTotal());
			couponDb.setValue(coupon.getValue());
			couponDb.setType(coupon.getType());
			couponDb.setIsAtLeast(coupon.isIsAtLeast());
			couponDb.setIsShare(coupon.isIsShare());
			couponDb.setAtLeast(coupon.getAtLeast());
			couponDb.setCardId(coupon.getCardId());
			couponDb.setQuota(coupon.getQuota());
			couponDb.setDescription(coupon.getDescription());
			couponDb.setServicePhone(coupon.getServicePhone());
			couponDb.setStartAt(coupon.getStartAt());
			couponDb.setEndAt(coupon.getEndAt());
			couponMapper.updateAllColumnById(couponDb);
		}else{
			// 优惠券别名，用于领取的时候替代ID
			coupon.setAlias(UUID.randomUUID().toString().replaceAll("-", ""));
			coupon.setUrl(gunsProperties.getClientUrl() + "/coupon/tofetch/" + coupon.getAlias());
			couponMapper.insert(coupon);
		}
		
		// 保存优惠券可用店铺
		storeCouponService.saveStoreCoupon(coupon.getId(), storeIds);
	}
}
