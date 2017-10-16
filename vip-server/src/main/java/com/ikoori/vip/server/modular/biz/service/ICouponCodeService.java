package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.CouponCode;

/**
 * 券码管理Service
 *
 * @author chengxg
 * @Date 2017-10-13 15:00:23
 */
public interface ICouponCodeService {
	public Integer deleteById(Long id);

	public Integer updateById(CouponCode couponCode);

	public CouponCode selectById(Long id);

	public Integer insert(CouponCode couponCode);

	List<Map<String, Object>> getCouponCodeList(Page<Map<String, Object>> page, String batchNo, String verifyCode,
			String verifyNo, Integer useStatus, Long couponId, Long merchantId, String orderByField, boolean isAsc);

	public String genarateCode(Long merchantId, Integer num);
	
	public void deleteByIds(Long [] ids);

	public void updateByBatchNo(String batchNo, Long merchantId, Integer useStatus);

	public void updateUseStatus(Map<String, Object> params);

	public List<CouponCode> selectByBatchNo(String batchNo, Long merchantId, Integer useStatus);

}
