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
	
	public void deleteEmployee(long storeEmployeeId);
	
	public void saveEmployee(StoreEmployee storeEmployee, String password, String sex);
	
	List<Map<String, Object>> getStoreEmployeeList(Page<Map<String, Object>> page, String employeeName,String mobile,Long storeId,Long roleId ,String orderByField,
			boolean isAsc);
}
