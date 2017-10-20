package com.ikoori.vip.server.modular.biz.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.RedpackSendType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
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
		Redpack rpack=new Redpack();
		rpack.setPackType(redpack.getPackType());
		rpack=redpackMapper.selectOne(rpack);
		
		// 根据发放类型，清除金额
		BigDecimal cleanAmount = new BigDecimal("0");
		if (redpack.getSendType().intValue() == RedpackSendType.fixed.getCode()) {
			redpack.setMinAmount(cleanAmount);
			redpack.setMaxAmount(cleanAmount);
		} else {
			redpack.setAmount(cleanAmount);
		}
		
		if (redpack.getId() == null) {
		 	if(rpack!=null){
	    		throw new BussinessException(BizExceptionEnum.EXISTED_PACKTYPE);
	    	}else{
	    		redpackMapper.insert(redpack);
	    	}
		} else {
			/*
			 * Redpack rpack =redpackService.selectById(redpack.getId());
			 * 
			 * Redpack repack=new Redpack();
			 * repack.setPackType(redpack.getPackType());
			 * repack=redpackMapper.selectOne(repack);
			 * 
			 * if (rpack.getPackType() == redpack.getPackType()) {
			 * redpackService.saveRedPack(redpack); return super.SUCCESS_TIP; }
			 * else { if(rpack!=null){ throw new
			 * BussinessException(BizExceptionEnum.EXISTED_PACKTYPE); }else{
			 * redpackService.saveRedPack(redpack); return super.SUCCESS_TIP; }
			 * }
			 */
			if (rpack.getPackType() == redpack.getPackType() || rpack==null) {
				redpackMapper.updateById(redpack);
			}else{
				throw new BussinessException(BizExceptionEnum.EXISTED_PACKTYPE);
			}
			
		}
	}

	@Override
	public List<Redpack> selectByMerchantId(Long merchantId) {
		Wrapper<Redpack> w = new EntityWrapper<>();
		w.eq("merchant_id", merchantId);
		w.eq("status", 1);
		return redpackMapper.selectList(w);
	}
}

