package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.CardRight;

/**
 * 会员权益Service
 *
 * @author chengxg
 * @Date 2017-08-07 10:50:13
 */
public interface ICardRightService {
	public Integer deleteById(Long id);
	public Integer updateById(CardRight cardRight);
	public CardRight selectById(Long id);
	public Integer insert(CardRight cardRight);
	List<Map<String, Object>> getCardRightList(@Param("page") Page<CardRight> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
	public List<CardRight> selectByCondition(Map<String,Object> condition);
	public List<CardRight> selectByCardId(Long cardId);
}
