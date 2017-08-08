package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.StoreMapper;
import com.ikoori.vip.common.persistence.model.Store;
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
	
	@Override
	public List<Map<String, Object>> getStoreList(Page<Store> page, String name, String orderByField,
			boolean isAsc) {
		return storeDao.getStoreList(page, name, orderByField, isAsc);
	}
}
