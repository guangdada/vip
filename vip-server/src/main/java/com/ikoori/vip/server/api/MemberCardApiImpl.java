package com.ikoori.vip.server.api;

import java.util.Map;

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
import com.ikoori.vip.server.modular.biz.service.IMemberCardService;
import com.ikoori.vip.server.modular.biz.service.IMemberService;

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
	@Autowired
	IMemberCardService memberCardService;
	
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
		if(merchant == null){
			return null;
		}
		if(card == null){
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

	/**   
	 * @Title: selectByMemberId 
	 * @Description: 会员卡详情  
	 * @date:   2017年9月20日 下午5:06:48 
	 * @author: huanglin    
	 * @throws   
	 */  
	@Override
	public JSONObject selectByMemberId(String openId) {
		Member member = memberDao.getMemberByOpenId(openId);
		if(member == null){
			return null;
		}
		
		Map<String,Object> cardDetail=memberCardService.selectByMemberId(member.getId());
		if(cardDetail==null){
			return null;
		}
		JSONObject obj = new JSONObject();
		obj.put("description", cardDetail.get("description"));
		obj.put("servicePhone", cardDetail.get("servicePhone"));
		obj.put("termType", cardDetail.get("termType"));
		obj.put("termDays", cardDetail.get("termDays"));
		obj.put("termStartAt", cardDetail.get("termStartAt"));
		obj.put("termEndAt", cardDetail.get("termEndAt"));
		return obj;
	}

}
