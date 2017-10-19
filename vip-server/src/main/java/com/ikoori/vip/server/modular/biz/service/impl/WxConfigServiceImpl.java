package com.ikoori.vip.server.modular.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ikoori.vip.common.persistence.dao.WxConfigMapper;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.WxConfig;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IWxConfigService;

/**
 * 公众号管理Dao
 *
 * @author fengshuonan
 * @Date 2017-07-31 09:58:11
 */
@Service
public class WxConfigServiceImpl implements IWxConfigService {
	@Autowired
	WxConfigMapper WxConfigMapper;
	
	@Autowired
	IMerchantService merchantService;
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveWxConfig(WxConfig wxConfig){
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		wxConfig.setMerchantId(merchant == null ? null :merchant.getId());
		WxConfigMapper.insert(wxConfig);
	}

}
