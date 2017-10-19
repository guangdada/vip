package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.StoreCouponMapper;
import com.ikoori.vip.common.persistence.model.StoreCoupon;
import com.ikoori.vip.server.modular.biz.dao.StoreCouponDao;
import com.ikoori.vip.server.modular.biz.service.IStoreCouponService;

/**
 * 优惠券可用店铺Dao
 *
 * @author chengxg
 * @Date 2017-09-23 14:07:11
 */
@Service
public class StoreCouponServiceImpl implements IStoreCouponService {
	@Autowired
	StoreCouponDao storeCouponDao;
	@Autowired
	StoreCouponMapper storeCouponMapper;
	@Override
	public Integer deleteById(Long id) {
		return storeCouponMapper.deleteById(id);
	}

	@Override
	public Integer updateById(StoreCoupon storeCoupon) {
		return storeCouponMapper.updateById(storeCoupon);
	}

	@Override
	public StoreCoupon selectById(Long id) {
		return storeCouponMapper.selectById(id);
	}

	@Override
	public Integer insert(StoreCoupon storeCoupon) {
		return storeCouponMapper.insert(storeCoupon);
	}
	
	@Override
	public List<Map<String, Object>> getStoreCouponList(Page<StoreCoupon> page, String name, String orderByField,
			boolean isAsc) {
		return storeCouponDao.getStoreCouponList(page, name, orderByField, isAsc);
	}
	
	/**
	 * 保存优惠券可用店铺
	 * @Title: saveStoreCoupon   
	 * @param couponId
	 * @param storeIds
	 * @date:   2017年9月23日 下午2:29:56 
	 * @author: chengxg
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveStoreCoupon(Long couponId,String storeIds){
		// 先删除优惠券可用店铺
		if(couponId != null){
			storeCouponDao.deleteByCouponId(couponId);	
		}
		// 保存优惠券可用店铺
		if (StringUtils.isNotBlank(storeIds)) {
			String[] ids = storeIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				StoreCoupon storeCoupon = new StoreCoupon();
				storeCoupon.setCouponId(couponId);
				storeCoupon.setStoreId(Long.valueOf(ids[i]));
				storeCouponMapper.insert(storeCoupon);
			}
		}
	}
	
	/**
	 * 获得优惠券可用店铺
	 * @Title: getByCouponId   
	 * @param couponId
	 * @return
	 * @date:   2017年9月23日 下午2:31:12 
	 * @author: chengxg
	 */
	public List<StoreCoupon> getByCouponId(Long couponId){
		return storeCouponDao.getByCouponId(couponId);
	}
}
