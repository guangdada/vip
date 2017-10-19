package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.StoreMapper;
import com.ikoori.vip.common.persistence.dao.StorePhotoMapper;
import com.ikoori.vip.common.persistence.model.Store;
import com.ikoori.vip.common.persistence.model.StorePhoto;
import com.ikoori.vip.common.util.RandomUtil;
import com.ikoori.vip.server.modular.biz.dao.StoreDao;
import com.ikoori.vip.server.modular.biz.service.IStoreService;

/**
 * 门店Dao
 *
 * @author chengxg
 * @Date 2017-08-07 17:52:18
 */
@Service
public class StoreServiceImpl implements IStoreService {
	@Autowired
	StoreDao storeDao;
	@Autowired
	StoreMapper storeMapper;
	@Autowired
	StorePhotoMapper storePhotoMapper;
	@Override
	public Integer deleteById(Long id) {
		return storeMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Store store) {
		return storeMapper.updateById(store);
	}

	@Override
	public Store selectById(Long id) {
		return storeMapper.selectById(id);
	}

	@Override
	public Integer insert(Store store) {
		return storeMapper.insert(store);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveStore(Store store, String pics) {
		if (store.getId() != null) {
			Wrapper<StorePhoto> wrapper = new EntityWrapper<StorePhoto>();
			wrapper.eq("store_id", store.getId());
			storePhotoMapper.delete(wrapper);
			storeMapper.updateById(store);
		} else {
			store.setStoreNo(RandomUtil.generateStoreNum());
			storeMapper.insert(store);
		}
		if (StringUtils.isNotBlank(pics)) {
			JSONArray picA = JSONArray.parseArray(pics);
			if (picA != null) {
				for (int i = 0; i < picA.size(); i++) {
					JSONObject pic = picA.getJSONObject(i);
					Long id = pic.getLong("id");
					StorePhoto storePhoto = new StorePhoto();
					storePhoto.setStoreId(store.getId());
					storePhoto.setPicId(id);
					storePhotoMapper.insert(storePhoto);
				}
			}
		}
	}
	
	@Override
	public List<Map<String, Object>> getStoreList(Page<Store> page, String name, String orderByField,
			boolean isAsc,Long merchantId) {
		return storeDao.getStoreList(page, name, orderByField, isAsc,merchantId);
	}

	@Override
	public List<Store> selectByCondition(Map<String, Object> condition) {
		return storeMapper.selectList(new EntityWrapper<Store>().eq("status", 1).eq("merchant_id", condition.get("merchantId")));
	}

	/**   
	 * @Title: selectStore 
	 * @Description: 店铺  
	 * @date:   2017年9月21日 下午5:48:50 
	 * @author: huanglin    
	 * @throws   
	 */  
	@Override
	public List<Store> selectStore(Long merchantId) {
		return storeDao.selectStore(merchantId);
		
	}
}
