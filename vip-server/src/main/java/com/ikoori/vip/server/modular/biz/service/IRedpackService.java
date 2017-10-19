package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Redpack;

/**
 * 红包Service
 *
 * @author chengxg
 * @Date 2017-10-19 11:30:26
 */
public interface IRedpackService {
	public Integer deleteById(Long id);
	public Integer updateById(Redpack redpack);
	public Redpack selectById(Long id);
	public Integer insert(Redpack redpack);
	List<Map<String, Object>> getRedpackList(Page<Map<String, Object>> page, String name,Integer packType,Integer sendType,Long merchantId,String orderByField,
			boolean isAsc);
	public void saveRedPack(Redpack redpack);
	
}
