package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.CardRightMapper;
import com.ikoori.vip.common.persistence.model.CardRight;
import com.ikoori.vip.server.modular.biz.dao.CardRightDao;
import com.ikoori.vip.server.modular.biz.service.ICardRightService;

/**
 * 会员权益Dao
 *
 * @author chengxg
 * @Date 2017-08-07 10:50:13
 */
@Service
public class CardRightServiceImpl implements ICardRightService {
	@Autowired
	CardRightDao cardRightDao;
	@Autowired
	CardRightMapper cardRightMapper;
	@Override
	public Integer deleteById(Long id) {
		return cardRightMapper.deleteById(id);
	}

	@Override
	public Integer updateById(CardRight cardRight) {
		return cardRightMapper.updateById(cardRight);
	}

	@Override
	public CardRight selectById(Long id) {
		return cardRightMapper.selectById(id);
	}

	@Override
	public Integer insert(CardRight cardRight) {
		return cardRightMapper.insert(cardRight);
	}
	
	@Override
	public List<Map<String, Object>> getCardRightList(Page<CardRight> page, String name, String orderByField,
			boolean isAsc) {
		return cardRightDao.getCardRightList(page, name, orderByField, isAsc);
	}

	@Override
	public List<CardRight> selectByCondition(Map<String, Object> condition) {
		Wrapper<CardRight> w = new EntityWrapper<CardRight>().eq("status", 1);
		if(condition.get("cardId") != null){
			w.eq("card_id", condition.get("cardId"));
		}
		return cardRightMapper.selectList(w);
	}
}
