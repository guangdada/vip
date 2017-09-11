package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.CouponTrade;

/**
 * 使用记录Service
 *
 * @author chengxg
 * @Date 2017-08-14 16:15:04
 */
public interface ICouponTradeService {
	public Integer deleteById(Long id);
	public Integer updateById(CouponTrade couponTrade);
	public CouponTrade selectById(Long id);
	public Integer insert(CouponTrade couponTrade);
	List<Map<String, Object>> getCouponTradeList(Page<CouponTrade> page,String name,String orderByField,boolean isAsc,Long merchantId);
	public List<Map<String, Object>> selectByCondition(String nickname,Integer type ,String mobile,Page<Object> page, String name, String orderByField,
			boolean isAsc,Long merchantId);
}
