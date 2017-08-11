package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.StoreEmployee;

/**
 * 员工管理Service
 *
 * @author chengxg
 * @Date 2017-08-09 11:12:10
 */
public interface IStoreEmployeeService {
	public Integer deleteById(Long id);
	public Integer updateById(StoreEmployee storeEmployee);
	public StoreEmployee selectById(Long id);
	public Integer insert(StoreEmployee storeEmployee);
	public void saveEmployee(StoreEmployee storeEmployee, String mobile, String password, String sex, String roleType,
			String name, String store, Long createUserId, Merchant merchant);
	List<Map<String, Object>> getStoreEmployeeList(@Param("page") Page<StoreEmployee> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
	
}
