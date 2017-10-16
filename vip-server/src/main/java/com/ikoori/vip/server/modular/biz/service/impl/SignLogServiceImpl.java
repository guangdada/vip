package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.SignLogMapper;
import com.ikoori.vip.common.persistence.model.SignLog;
import com.ikoori.vip.server.modular.biz.dao.SignLogDao;
import com.ikoori.vip.server.modular.biz.service.ISignLogService;

/**
 * 签到记录Dao
 *
 * @author chengxg
 * @Date 2017-09-28 21:50:31
 */
@Service
public class SignLogServiceImpl implements ISignLogService {
	@Autowired
	SignLogDao signLogDao;
	@Autowired
	SignLogMapper signLogMapper;

	@Override
	public Integer deleteById(Long id) {
		return signLogMapper.deleteById(id);
	}

	@Override
	public Integer updateById(SignLog signLog) {
		return signLogMapper.updateById(signLog);
	}

	@Override
	public SignLog selectById(Long id) {
		return signLogMapper.selectById(id);
	}

	@Override
	public Integer insert(SignLog signLog) {
		return signLogMapper.insert(signLog);
	}
	
	public void saveSignlog(Long memberId,Long merchantId){
		
	}

	@Override
	public List<Map<String, Object>> getSignLogList(Page<Map<String, Object>> page, String name, String nickname, String mobile,
			String signS, String signE, Long merchantId, String orderByField, boolean isAsc) {
		return signLogDao.getSignLogList(page, name, nickname, mobile, signS, signE, merchantId, orderByField, isAsc);
	}
}
