package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.service.IMemberService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;

/**
 * 会员Dao
 *
 * @author chengxg
 * @Date 2017-08-02 12:31:42
 */
@Service
public class MemberServiceImpl implements IMemberService {

	@Autowired
	MemberMapper memberMapper;
	@Autowired
	MemberDao memberDao;
	@Autowired
	MemberCardMapper memberCardMapper;
    @Autowired
	IMerchantService merchantService;
	@Override
	public Integer deleteById(Long id) {
		return memberMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Member member) {
		return memberMapper.updateById(member);
	}

	@Override
	public Member selectById(Long id) {
		return memberMapper.selectById(id);
	}
	
	@Override
	public Member selecByMobile(String mobile){
		Member member = new Member();
		member.setMobile(mobile);
		return memberMapper.selectOne(member);
	}

	@Override
	public Integer insert(Member member) {
		return memberMapper.insert(member);
	}


	@Override
	@Transactional(readOnly=false)
	public void saveMember(Member member, Long cardId) {
		//当前登录账号
    	Long createUserId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(createUserId);
    	/*member.setIsActive(false);*/
    	this.insert(member);
		MemberCard mc=new MemberCard();
        mc.setMemberId(member.getId());
        mc.setMerchantId(merchant.getId());
        mc.setCardId(cardId);
        mc.setCardNumber("");
        memberCardMapper.insert(mc);
	}

	@Override
	@Transactional(readOnly=false)
	public void deleteMember(Long memberId) {
		Member member=this.selectById(memberId);
		MemberCard mc=new MemberCard();
	    mc.setMemberId(member.getId());
		mc=memberCardMapper.selectOne(mc);
		memberCardMapper.deleteById(mc.getId());
		this.deleteById(memberId);
	}

	@Override
	@Transactional(readOnly=false)
	public void updateMember(Member member,Long cardId) {
		this.updateById(member);
		MemberCard mc=new MemberCard();
	    mc.setMemberId(member.getId());
	    mc=memberCardMapper.selectOne(mc);
	    mc.setCardId(cardId);
		memberCardMapper.updateById(mc);
	}
	
	public Member selectByMobileAndStoreNo(String mobile, String storeNo){
		return memberDao.selectByMobileAndStoreNo(mobile, storeNo);
	}

	/**   
	 * <p>Title: getMemberList</p>   
	 * <p>Description: 按条件查找会员</p>   
	 * @param page
	 * @param memName 会员姓名
	 * @param memSex 会员性别
	 * @param memNickName 会员昵称
	 * @param memMobile 会员手机号
	 * @param orderByField
	 * @param isAsc
	 * @return   
	 * @see com.ikoori.vip.server.modular.biz.service.IMemberService#getMemberList(com.baomidou.mybatisplus.plugins.Page, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, boolean)   
	 */  
	@Override
	public List<Map<String, Object>> getMemberList(Page<Map<String, Object>> page, String memName, Integer memSex,
			String memNickName, String memMobile, String orderByField, boolean isAsc) {
		return memberDao.getMemberList(page, memName, memSex, memNickName, memMobile, orderByField, isAsc);
	}
}
