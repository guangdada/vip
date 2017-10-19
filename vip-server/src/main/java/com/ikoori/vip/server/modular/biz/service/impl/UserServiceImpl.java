package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.UserMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.User;
import com.ikoori.vip.server.modular.biz.service.IUserService;
import com.ikoori.vip.server.modular.system.dao.UserMgrDao;

/**
 * 会员
 *
 * @author chengxg
 * @Date 2017-08-04 11:07:42
 */
@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	UserMapper userMapper;
	@Resource
    private UserMgrDao managerDao;
	@Override
	public Integer deleteById(Long id) {
		// TODO Auto-generated method stub
		return userMapper.deleteById(id);
	}

	@Override
	public Integer updateById(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateById(user);
	}

	@Override
	public User selectById(Long id) {
		// TODO Auto-generated method stub
		return userMapper.selectById(id);
	}

	@Override
	public Integer insert(User user) {
		// TODO Auto-generated method stub
		return userMapper.insert(user);
	}

	@Override
	public List<User> selectByCondition(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getUserList(Page<Card> page, String name, String orderByField, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}
}
