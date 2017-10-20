package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.RedpackSendType;
import com.ikoori.vip.common.persistence.dao.RedpackMapper;
import com.ikoori.vip.common.persistence.model.Redpack;
import com.ikoori.vip.server.modular.biz.dao.RedpackDao;
import com.ikoori.vip.server.modular.biz.service.IRedpackService;

/**
 * 红包Dao
 *
 * @author chengxg
 * @Date 2017-10-19 11:30:26
 */
@Service
public class RedpackServiceImpl implements IRedpackService {
	@Autowired
	RedpackDao redpackDao;
	@Autowired
	RedpackMapper redpackMapper;
	@Override
	public Integer deleteById(Long id) {
		return redpackMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Redpack redpack) {
		return redpackMapper.updateById(redpack);
	}

	@Override
	public Redpack selectById(Long id) {
		return redpackMapper.selectById(id);
	}

	@Override
	public Integer insert(Redpack redpack) {
		return redpackMapper.insert(redpack);
	}

	@Override
	public List<Map<String, Object>> getRedpackList(Page<Map<String, Object>> page, String name, Integer packType,
			Integer sendType, Long merchantId, String orderByField, boolean isAsc) {
		return redpackDao.getRedpackList(page, name, packType,sendType,merchantId,orderByField, isAsc);
	}

	@Override
	public void saveRedPack(Redpack redpack) {
		// 已有同类型的红包， 不能再添加
		// 根据发放类型，清除金额
		if (redpack.getSendType().intValue() == RedpackSendType.fixed.getCode()) {
			//redpack.setAmount(redpack.getAmount() * 100);
			redpack.setMinAmount(0);
			redpack.setMaxAmount(0);
		} else {
			redpack.setAmount(0);
			//redpack.setMinAmount(redpack.getMinAmount() * 100);
			//redpack.setMaxAmount(redpack.getMaxAmount() * 100);
		}
		if (redpack.getId() == null) {
			redpackMapper.insert(redpack);
		} else {
			redpackMapper.updateById(redpack);
		}
	}
}

