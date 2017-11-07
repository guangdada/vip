package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ikoori.vip.common.constant.cache.Cache;
import com.ikoori.vip.common.constant.cache.CacheKey;
import com.ikoori.vip.common.constant.state.ManagerStatus;
import com.ikoori.vip.common.constant.state.MerchantState;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.MerchantMapper;
import com.ikoori.vip.common.persistence.dao.UserMapper;
import com.ikoori.vip.common.persistence.dao.WxUserMapper;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.User;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.system.dao.UserMgrDao;

/**
 * 商户Dao
 *
 * @author fengshuonan
 * @Date 2017-07-28 13:09:10
 */
@Service
public class MerchantServiceImpl implements IMerchantService {
	@Autowired
	MerchantMapper merchantMapper;
	@Autowired
	UserMgrDao managerDao;
	@Autowired
	UserMapper userMapper;
	@Autowired
	WxUserMapper wxUserMapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	GunsProperties gunsProperties;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveMerchant(Merchant merchant){
		if(merchant.getId() == null){
			User theUser = managerDao.getByAccount(merchant.getMobile());
			if(theUser != null){
				throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
			}
			User user = new User();
			// 完善账号信息
			user.setAccount(merchant.getMobile());
			user.setName(merchant.getName());
	        user.setSalt(ShiroKit.getRandomSalt(5));
	        user.setPassword(ShiroKit.md5(ShiroKit.DEFAULTPWD, user.getSalt()));
	        user.setStatus(ManagerStatus.OK.getCode());
	        user.setPhone(merchant.getMobile());
	        user.setCreatetime(new Date());
	        user.setRoleid(gunsProperties.getMerchantRoleId());
	        user.setAvatar(merchant.getHeadImg());
	        user.insert();
	        merchant.setUserId(Long.valueOf(user.getId()));
	        merchant.setState(MerchantState.YES.getCode());
			merchantMapper.insert(merchant);
		}else{
			Merchant dbMerchant = merchantMapper.selectById(merchant.getId());
			User dbUser = userMapper.selectById(dbMerchant.getUserId());
			if(!checkMobile(dbUser.getId(), merchant.getMobile())){
				throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
			}
			dbUser.setName(merchant.getName());
			dbUser.setAvatar(merchant.getHeadImg());
			dbUser.updateById();
			merchantMapper.updateById(merchant);
		}
	}
	
	public boolean checkMobile(Integer id, String mobile) {
		Wrapper<User> user = new EntityWrapper<User>();
		if (id != null) {
			user.ne("id", id);
		}
		if (StringUtils.isNotBlank(mobile)) {
			user.eq("account", mobile);
		}
		return userMapper.selectCount(user) == 0;
	}
	
	@Cacheable(value = Cache.MERCHANT, key = "'" + CacheKey.SINGLE_MERCHANT + "'+#userId")
	public Merchant getMerchantUserId(Long userId){
		Merchant merchant = new Merchant();
		merchant.setUserId(userId);
		return merchantMapper.selectOne(merchant);
	}
	
	public Merchant getCurrentMerchant(){
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		return getMerchantUserId(userId);
	}
}
