package com.ikoori.vip.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.service.MemberCardApi;
import com.ikoori.vip.common.persistence.dao.CardMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.MerchantMapper;
import com.ikoori.vip.common.persistence.dao.WxUserMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.server.modular.biz.dao.MemberCardDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;

@Service
public class MemberCardApiImpl implements MemberCardApi {
	@Autowired
	MemberCardMapper memberCardMapper;
	@Autowired
	MemberCardDao memberCardDao;
	@Autowired
	MemberDao memberDao;
	@Autowired
	WxUserMapper wxUserMapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	CardMapper cardMapper;
	@Autowired
	MerchantMapper merchantMapper;
	
	@Override
	public JSONObject getMemberCardByOpenId(String openId) {
		Member member = memberDao.getMemberByOpenId(openId);
		if(member == null){
			return null;
		}
		MemberCard memberCard = memberCardDao.getMemberCard(member.getId());
		if(memberCard == null){
			return null;
		}
		Merchant merchant = memberCard.getMerchant();
		Card card = memberCard.getCard();
		if (merchant == null || card == null) {
			return null;
		}
		JSONObject obj = new JSONObject();
		obj.put("coverPic", card.getCoverPic());
		obj.put("colorCode", card.getColorCode());
		obj.put("merchantName", merchant.getName());
		obj.put("merchantLogo", merchant.getHeadImg());
		obj.put("cardName", card.getName());
		obj.put("cardNum", memberCard.getCardNumber());
		return obj;
	}

}
