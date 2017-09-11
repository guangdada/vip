package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikoori.vip.common.constant.state.ManagerStatus;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.MerchantMapper;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.User;
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
	
	@Transactional(readOnly = false)
	public void saveMerchant(Merchant merchant){
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
        user.setRoleid(ShiroKit.merchantRoleId);
        user.insert();
        
        
        merchant.setUserId(Long.valueOf(user.getId()));
		merchantMapper.insert(merchant);
		
	}
	
	public Merchant getMerchantUserId(Long userId){
		Merchant merchant = new Merchant();
		merchant.setUserId(userId);
		return merchantMapper.selectOne(merchant);
		/*List<Merchant> mlist = merchantMapper.selectList(new EntityWrapper<Merchant>().eq("user_id", userId));
		return CollectionUtils.isEmpty(mlist) ? null : mlist.get(0);*/
	}
	
	public Long getCurrentMerchantId(){
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		return getMerchantUserId(userId).getId();
	}
}
