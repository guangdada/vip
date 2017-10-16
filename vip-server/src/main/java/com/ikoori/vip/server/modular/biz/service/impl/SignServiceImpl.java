package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.SignMapper;
import com.ikoori.vip.common.persistence.model.Sign;
import com.ikoori.vip.server.modular.biz.dao.SignDao;
import com.ikoori.vip.server.modular.biz.service.ISignService;

/**
 * 签到规则Dao
 *
 * @author chengxg
 * @Date 2017-09-28 21:50:21
 */
@Service
public class SignServiceImpl implements ISignService {
	@Autowired
	SignDao signDao;
	@Autowired
	SignMapper signMapper;
	@Override
	public Integer deleteById(Long id) {
		return signMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Sign sign) {
		Sign signDb = signMapper.selectById(sign.getId());
		if(!checkSign(signDb.getMerchantId(), sign.getId(), sign.getTimes())){
			throw new BussinessException(500, "签到次数已经存在");
		}
		return signMapper.updateById(sign);
	}

	@Override
	public Sign selectById(Long id) {
		return signMapper.selectById(id);
	}

	@Override
	public Integer insert(Sign sign) {
		if(!this.checkSign(sign.getMerchantId(), null, sign.getTimes())){
			throw new BussinessException(500, "签到次数已经存在");
		}
		return signMapper.insert(sign);
	}
	
	@Override
	public List<Map<String, Object>> getSignList(Page<Sign> page, String name, String orderByField,
			boolean isAsc,Long merchantId) {
		return signDao.getSignList(page, name, orderByField, isAsc,merchantId);
	}
	
	public boolean checkSign(Long merchantId, Long signId, Integer times) {
		Wrapper<Sign> sign = new EntityWrapper<>();
		sign.eq("merchant_id", merchantId);
		sign.eq("status", 1);
		if (signId != null) {
			sign.ne("id", signId);
		}
		if (times != null) {
			sign.eq("times", times);
		}
		return signMapper.selectCount(sign) == 0;
	}
	
}
