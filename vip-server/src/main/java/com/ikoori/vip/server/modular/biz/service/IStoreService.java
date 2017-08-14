package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Store;
import com.ikoori.vip.common.persistence.model.StoreEmployee;

/**
 * 门店Service
 *
 * @author chengxg
 * @Date 2017-08-07 17:52:18
 */
public interface IStoreService {
	public Integer deleteById(Long id);
	public Integer updateById(Store store);
	public Store selectById(Long id);
	public Integer insert(Store store);
	public List<Store> selectByCondition(Map<String, Object> condition);
	List<Map<String, Object>> getStoreList(@Param("page") Page<Store> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,@Param("merchantId") Long merchantId);
	
}
