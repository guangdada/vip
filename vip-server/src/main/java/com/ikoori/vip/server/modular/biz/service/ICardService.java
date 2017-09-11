package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Card;

/**
 * 会员卡Service
 *
 * @author chengxg
 * @Date 2017-08-04 11:07:42
 */
public interface ICardService {
	public Integer deleteById(Long id);
	public Integer updateById(Card card);
	public Card selectById(Long id);
	public Integer insert(Card card);
	public List<Card> selectByCondition(Map<String, Object> condition);
	List<Map<String, Object>> getCardList(@Param("page") Page<Card> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,@Param("merchantId") Long merchantId);
	public void saveCard(Card card, String rights);
	public boolean checkCardName(Long id, String cardName,Long merchantId);
	public boolean checkCardLevel(Long id, Integer cardLevel,Long merchantId);
}
