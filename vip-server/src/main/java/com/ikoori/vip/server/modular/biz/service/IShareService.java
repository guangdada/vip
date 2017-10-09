package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Share;

/**
 * 分享规则Service
 *
 * @author chengxg
 * @Date 2017-10-16 14:22:06
 */
public interface IShareService {
	public Integer deleteById(Long id);

	public Integer updateById(Share share);

	public Share selectById(Long id);

	public Integer insert(Share share);

	List<Map<String, Object>> getShareList(Page<Map<String, Object>> page, Long merchantId, String orderByField,
			boolean isAsc);

	public void saveShare(Share share);

	public void activeShare(String receiveOpenid);

}
