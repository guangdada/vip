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
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.RightType;
import com.ikoori.vip.common.persistence.dao.CardMapper;
import com.ikoori.vip.common.persistence.dao.CardRightMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.CardRight;
import com.ikoori.vip.common.util.DateUtil;
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
		card.setCreateUserId(Long.valueOf(ShiroKit.getUser().getId()));
		if (StringUtils.isNotBlank(card.getTermStartAtStr())) {
			card.setTermStartAt(DateUtil.parseDate(card.getTermStartAtStr()));
		}
		if (StringUtils.isNotBlank(card.getTermEndAtStr())) {
			card.setTermEndAt(DateUtil.parseDate(card.getTermEndAtStr()));
		}
		card.setCardNumberPrefix("");
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
}
