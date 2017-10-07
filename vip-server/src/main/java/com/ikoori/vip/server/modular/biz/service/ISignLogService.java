package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.SignLog;

/**
 * 签到记录Service
 *
 * @author chengxg
 * @Date 2017-09-28 21:50:31
 */
public interface ISignLogService {
	public Integer deleteById(Long id);

	public Integer updateById(SignLog signLog);

	public SignLog selectById(Long id);

	public Integer insert(SignLog signLog);

	List<Map<String, Object>> getSignLogList(Page<Map<String, Object>> page, String name, String nickname, String mobile,
			String signS, String signE, Long merchantId, String orderByField, boolean isAsc);

}
