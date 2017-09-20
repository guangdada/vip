package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.MemCardState;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.server.modular.biz.dao.MemberCardDao;
import com.ikoori.vip.server.modular.biz.service.IMemberCardService;

/**
 * 领取记录Dao
 *
 * @author chengxg
 * @Date 2017-08-14 13:14:55
 */
@Service
public class MemberCardServiceImpl implements IMemberCardService {
	@Autowired
	MemberCardDao memberCardDao;
	@Autowired
	MemberCardMapper memberCardMapper;
	@Override
	public Integer deleteById(Long id) {
		return memberCardMapper.deleteById(id);
	}

	@Override
	public Integer updateById(MemberCard memberCard) {
		return memberCardMapper.updateById(memberCard);
	}

	@Override
	public MemberCard selectById(Long id) {
		return memberCardMapper.selectById(id);
	}

	@Override
	public Integer insert(MemberCard memberCard) {
		return memberCardMapper.insert(memberCard);
	}
	
	@Override
	public List<Map<String, Object>> getMemberCardList(Long cardId, Integer grantType, Integer state, String mobile,
			Page<MemberCard> page, String nickname, String orderByField, boolean isAsc, Long merchantId,
			String cardNumber) {
		return memberCardDao.getMemberCardList(cardId, grantType, state, mobile, page, nickname, orderByField, isAsc,
				merchantId, cardNumber);
	}
	
	public List<MemberCard> findByMemberId(Long memberId){
		Wrapper<MemberCard> wrapper = new EntityWrapper<MemberCard>();
		wrapper.eq("member_id", memberId);
		wrapper.eq("state", MemCardState.USED.getCode());
		wrapper.eq("status", 1);
		return memberCardMapper.selectList(wrapper);
	}
	
	public Map<String,Object> selectByMemberId(Long memberId){
		return memberCardDao.selectByMemberId(memberId);
	}
}
