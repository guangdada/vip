package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.RightType;
import com.ikoori.vip.common.persistence.dao.CardMapper;
import com.ikoori.vip.common.persistence.dao.CardRightMapper;
import com.ikoori.vip.common.persistence.dao.UserMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.CardRight;
import com.ikoori.vip.common.persistence.model.User;
import com.ikoori.vip.common.util.DateUtil;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.dao.CardDao;
import com.ikoori.vip.server.modular.biz.service.ICardService;
import com.ikoori.vip.server.modular.biz.service.IUserService;
import com.ikoori.vip.server.modular.system.dao.UserMgrDao;

/**
 * 会员卡Dao
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
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}
}
