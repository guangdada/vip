package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.dto.CouponFetchDo;
import com.ikoori.vip.common.persistence.model.CouponFetch;

/**
 * 领取记录Service
 *
 * @author chengxg
 * @Date 2017-08-14 16:14:52
 */
public interface ICouponFetchService {
	public Integer deleteById(Long id);
	public Integer updateById(CouponFetch couponFetch);
	public CouponFetch selectById(Long id);
	public Integer insert(CouponFetch couponFetch);
	List<Map<String, Object>> getCouponFetchList(Page<CouponFetch> page, String name,String orderByField,boolean isAsc,Long merchantId);
	public List<Map<String, Object>> selectByCondition(Page<CouponFetchDo> page, String name, String orderByField,
			boolean isAsc,Long merchantId);
}
