package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.ManagerStatus;
import com.ikoori.vip.common.constant.state.RoleType;
import com.ikoori.vip.common.persistence.dao.StoreEmployeeMapper;
import com.ikoori.vip.common.persistence.dao.UserMapper;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.StoreEmployee;
import com.ikoori.vip.common.persistence.model.User;
import com.ikoori.vip.server.core.shiro.ShiroKit;
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
	@Resource
	private UserMapper userMapper;
	@Autowired
	IStoreEmployeeService storeEmployeeService;
	   
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
	@Transactional(readOnly=false)
	public void saveEmployee(StoreEmployee storeEmployee,String password, String sex) {
		if(storeEmployee.getId() == null){
			User user=new User();
	        user.setSalt(ShiroKit.getRandomSalt(5));
	        user.setPassword(ShiroKit.md5(password, user.getSalt()));
	        user.setName(storeEmployee.getName());
	        user.setAccount(storeEmployee.getMobile());
	        user.setRoleid(storeEmployee.getRoleId()+"");
	        user.setPhone(storeEmployee.getMobile());
	        user.setSex(Integer.valueOf(sex));
	        user.setStatus(ManagerStatus.OK.getCode());
	        user.setCreatetime(new Date());
	        userMapper.insert(user);
	        long userId=user.getId().longValue();
	        storeEmployee.setUserId(userId);
	    	this.insert(storeEmployee);
		}else{
			StoreEmployee dbEmp = storeEmployeeMapper.selectById(storeEmployee.getId());
			User dbUser = userMapper.selectById(dbEmp.getUserId());
			dbUser.setName(storeEmployee.getName());
			dbUser.setRoleid(storeEmployee.getRoleId()+"");
			dbUser.setPhone(storeEmployee.getMobile());
			dbUser.setSex(Integer.valueOf(sex));
	        userMapper.updateById(dbUser);
	        storeEmployeeMapper.updateById(storeEmployee);
		}
	}

	@Override
	public void deleteEmployee(long storeEmployeeId) {
		// TODO Auto-generated method stub
		StoreEmployee storeEmployee=storeEmployeeService.selectById(storeEmployeeId);
    	userMapper.deleteById(storeEmployee.getUserId().intValue());
        storeEmployeeService.deleteById(storeEmployeeId);
	}
}
