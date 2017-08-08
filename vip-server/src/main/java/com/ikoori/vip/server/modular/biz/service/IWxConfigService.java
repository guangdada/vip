package com.ikoori.vip.server.modular.biz.service;

import com.ikoori.vip.common.persistence.model.WxConfig;

/**
 * 公众号管理Service
 *
 * @author fengshuonan
 * @Date 2017-07-31 09:58:11
 */
public interface IWxConfigService {
	public void saveWxConfig(WxConfig wxConfig);
}
