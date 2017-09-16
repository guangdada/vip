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
	
	/**
	 * 保存会员卡
	 * @Title: saveCard   
	 * @param card
	 * @param rights
	 * @date:   2017年9月14日 上午11:41:40 
	 * @author: chengxg
	 */
	public void saveCard(Card card, String rights);
	
	/**
	 * 判断会员卡名成功是否存在
	 * @Title: checkCardName   
	 * @param id
	 * @param cardName
	 * @param merchantId
	 * @return
	 * @date:   2017年9月14日 上午11:41:55 
	 * @author: chengxg
	 */
	public boolean checkCardName(Long id, String cardName,Long merchantId);
	
	/**
	 * 判断会员卡级别是否存在
	 * @Title: checkCardLevel   
	 * @param id
	 * @param cardLevel
	 * @param merchantId
	 * @return
	 * @date:   2017年9月14日 上午11:42:15 
	 * @author: chengxg
	 */
	public boolean checkCardLevel(Long id, Integer cardLevel,Long merchantId);
}
