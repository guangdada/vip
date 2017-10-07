package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Sign;

/**
 * 签到规则Service
 *
 * @author chengxg
 * @Date 2017-09-28 21:50:21
 */
public interface ISignService {
	public Integer deleteById(Long id);
	public Integer updateById(Sign sign);
	public Sign selectById(Long id);
	public Integer insert(Sign sign);
	List<Map<String, Object>> getSignList(Page<Sign> page, String name,String orderByField, boolean isAsc,Long merchantId);
	
}
