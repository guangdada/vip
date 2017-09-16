package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.CardGrantType;
import com.ikoori.vip.common.constant.state.RightType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.CardMapper;
import com.ikoori.vip.common.persistence.dao.CardRightMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.CardRight;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.dao.CardDao;
import com.ikoori.vip.server.modular.biz.service.ICardService;

/**
 * 会员卡Dao
 *
 * @author chengxg
 * @Date 2017-08-04 11:07:42
 */
@Service
public class CardServiceImpl implements ICardService {
	@Autowired
	CardDao cardDao;
	@Autowired
	CardMapper cardMapper;
	@Autowired
	CardRightMapper cardRightMapper;
	@Autowired
	GunsProperties gunsProperties;

	@Override
	public Integer deleteById(Long id) {
		return cardMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Card card) {
		return cardMapper.updateById(card);
	}

	@Override
	public Card selectById(Long id) {
		return cardMapper.selectById(id);
	}

	@Override
	public Integer insert(Card card) {
		return cardMapper.insert(card);
	}
	
	public List<Card> selectByCondition(Map<String, Object> condition) {
		return cardMapper.selectList(new EntityWrapper<Card>().eq("status", 1).eq("merchant_id", condition.get("merchantId")));
	}

	@Override
	public List<Map<String, Object>> getCardList(Page<Card> page, String name, String orderByField, boolean isAsc,@Param("merchantId") Long merchantId) {
		return cardDao.getCardList(page, name, orderByField, isAsc,merchantId);
	}

	@Transactional(readOnly = false)
	public void saveCard(Card card, String rights) {
		if (card.getGrantType().intValue() == CardGrantType.RULE.getCode()
				&& !checkCardLevel(card.getId(), card.getCardLevel(), card.getMerchantId())) {
			throw new BussinessException(BizExceptionEnum.INVALID_cardLevel);
		}
		if (!checkCardName(card.getId(), card.getName(), card.getMerchantId())) {
			throw new BussinessException(BizExceptionEnum.INVALID_cardName);
		}

		if (card.getGrantType().intValue() == CardGrantType.SUB_WX.getCode()
				&& !checkSubWxCount(card.getId(), card.getMerchantId())) {
			throw new BussinessException(BizExceptionEnum.INVALID_grantType);
		}
		card.setCreateUserId(Long.valueOf(ShiroKit.getUser().getId()));
		if(card.getCoverType().intValue() == 1){
			card.setColorCode("");
		}else{
			card.setCoverPic("");
		}
		card.setCardNumberPrefix("KR");
		if(card.getId() != null){
			Integer c = cardMapper.updateById(card);
			if(c > 0){
				// 先删除修改前的权益值，再添加新的权益值
				cardRightMapper.delete(new EntityWrapper<CardRight>().eq("card_id",card.getId()));
				if (StringUtils.isNotBlank(rights)) {
					getRigthFromJson(rights, card.getId());
				}
			}
		}else{
			cardMapper.insert(card);
			if (StringUtils.isNotBlank(rights)) {
				getRigthFromJson(rights, card.getId());
			}
		}
	}

	private void getRigthFromJson(String rights, Long cardId) {
		List<CardRight> crs = new ArrayList<CardRight>();
		JSONArray rs = JSONArray.parseArray(rights);
		if (rs != null) {
			for (int i = 0; i < rs.size(); i++) {
				JSONObject r = rs.getJSONObject(i);
				String type = r.getString("type");
				if (type.equals(RightType.POINTS.getCode())) {
					CardRight cardRight = new CardRight();
					cardRight.setPoints(r.getInteger("points"));
					cardRight.setRightType(RightType.POINTS.getCode());
					cardRight.setCardId(cardId);
					cardRight.setIsAvailable(true);
					crs.add(cardRight);
				} else if (type.equals(RightType.DISCOUNT.getCode())) {
					CardRight cardRight = new CardRight();
					cardRight.setDiscount(r.getInteger("discount"));
					cardRight.setRightType(RightType.DISCOUNT.getCode());
					cardRight.setCardId(cardId);
					cardRight.setIsAvailable(true);
					crs.add(cardRight);
				} else if (type.equals(RightType.COUPON.getCode())) {
					JSONArray coupons = r.getJSONArray("coupon");
					if (coupons != null) {
						for (int c = 0; c < coupons.size(); c++) {
							CardRight cardRight = new CardRight();
							JSONObject coupon = coupons.getJSONObject(c);
							Long couponId = coupon.getLong("couponId");
							Integer number = coupon.getInteger("number");
							cardRight.setCouponId(couponId);
							cardRight.setNumber(number);
							cardRight.setRightType(RightType.COUPON.getCode());
							cardRight.setCardId(cardId);
							cardRight.setIsAvailable(true);
							crs.add(cardRight);
						}
					}
				}
			}
		}
		if (crs.size() >= 0) {
			for (CardRight cardRight : crs) {
				cardRightMapper.insert(cardRight);
			}
		}
	}
	
	public boolean checkCardName(Long id, String cardName,Long merchantId) {
		Wrapper<Card> card = new EntityWrapper<>();
		card.eq("merchant_id", merchantId);
		card.eq("status", 1);
		if (id != null) {
			card.ne("id", id);
		}
		if (StringUtils.isNotBlank(cardName)) {
			card.eq("name", cardName);
		}
		return cardMapper.selectCount(card) == 0;
	}
	
	public boolean checkCardLevel(Long id, Integer cardLevel,Long merchantId) {
		Wrapper<Card> card = new EntityWrapper<>();
		card.eq("grant_type",CardGrantType.RULE.getCode());
		card.eq("merchant_id", merchantId);
		card.eq("status", 1);
		if (id != null) {
			card.ne("id", id);
		}
		if (cardLevel != null) {
			card.eq("card_level", cardLevel);
		}
		return cardMapper.selectCount(card) == 0;
	}
	
	/**
	 * 判断“关注微信”类型会员卡只能有一种
	 * @Title: checkSubWxCount   
	 * @param id
	 * @param merchantId
	 * @return
	 * @date:   2017年9月14日 下午2:03:07 
	 * @author: chengxg
	 */
	public boolean checkSubWxCount(Long id,Long merchantId){
		Wrapper<Card> card = new EntityWrapper<>();
		card.eq("grant_type",CardGrantType.SUB_WX.getCode());
		card.eq("merchant_id", merchantId);
		card.eq("status", 1);
		if (id != null) {
			card.ne("id", id);
		}
		return cardMapper.selectCount(card) == 0;
	}
}
