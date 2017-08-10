package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.StoreEmployeeMapper;
import com.ikoori.vip.common.persistence.model.StoreEmployee;
import com.ikoori.vip.server.modular.biz.dao.StoreEmployeeDao;
import com.ikoori.vip.server.modular.biz.service.IStoreEmployeeService;

/**
 * 员工管理Dao
 *
 * @author chengxg
 * @Date 2017-08-09 11:12:10
 */
@Service
public class StoreEmployeeServiceImpl implements IStoreEmployeeService {
	@Autowired
	StoreEmployeeDao storeEmployeeDao;
	@Autowired
	StoreEmployeeMapper storeEmployeeMapper;
	@Override
	public Integer deleteById(Long id) {
		return storeEmployeeMapper.deleteById(id);
	}

	@Override
	public Integer updateById(StoreEmployee storeEmployee) {
		return storeEmployeeMapper.updateById(storeEmployee);
	}

	@Override
	public StoreEmployee selectById(Long id) {
		return storeEmployeeMapper.selectById(id);
	}

	@Override
	public Integer insert(StoreEmployee storeEmployee) {
		return storeEmployeeMapper.insert(storeEmployee);
	}
	
	@Override
	public List<Map<String, Object>> getStoreEmployeeList(Page<StoreEmployee> page, String name, String orderByField,
			boolean isAsc) {
		return storeEmployeeDao.getStoreEmployeeList(page, name, orderByField, isAsc);
	}
}
