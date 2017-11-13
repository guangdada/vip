package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.Store;

/**
 * 门店Dao
 *
 * @author chengxg
 * @Date 2017-08-07 17:52:18
 */
public interface StoreDao {
	List<Map<String, Object>> getStoreList(@Param("page") Page<Store> page, @Param("name") String name,
			@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,
			@Param("merchantId") Long merchantId);

	public Store selectByStoreNo(@Param("storeNo") String storeNo);

	List<Map<String, Object>> getStore(@Param("lat") double lat, @Param("lon") double lon,
			@Param("minLat") double minLat, @Param("minLng") double minLng, @Param("maxLat") double maxLat,
			@Param("maxLng") double maxLng);

	Store getStoreDetail(@Param("storeId") Long storeId);

	List<Map<String, Object>> getStoreByUnionid(@Param("unionid") String unionid, @Param("storeType") Integer storeType,
			@Param("start") Integer start, @Param("pageSize") Integer pageSize);

	public List<Store> selectStore(@Param("merchantId") Long merchantId);
}
