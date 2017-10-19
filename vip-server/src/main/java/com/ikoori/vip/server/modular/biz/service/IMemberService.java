package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Member;

/**
 * 会员Service
 *
 * @author chengxg
 * @Date 2017-08-02 12:31:42
 */
public interface IMemberService {
	public Integer deleteById(Long id);

	public Integer updateById(Member member);

	public Member selectById(Long id);

	public Member selectByMemberId(Long memberId);

	public Integer insert(Member member);

	public void saveMember(Member member, Long cardId);

	public void deleteMember(Long memberId);

	public void updateMember(Member member, Long cardId, int point);

	/**
	 * 分页查询
	 * @Title: getMemberList   
	 * @param page
	 * @param memName
	 * @param memSex
	 * @param memNickName
	 * @param memMobile
	 * @param cardId
	 * @param cardNumber
	 * @param orderByField
	 * @param isAsc
	 * @return
	 * @date:   2017年9月18日 下午1:56:28 
	 * @author: chengxg
	 */
	List<Map<String, Object>> getMemberList(Page<Map<String, Object>> page, String memName, Integer memSex,
			String memNickName, String memMobile, Long cardId, String cardNumber, Integer isActive,String openId,String orderByField, boolean isAsc);

	/**
	 * 根据手机号和店铺编号查询会员
	 * @Title: selectByMobile
	 * @param mobile
	 * @param storeNo
	 * @return
	 * @date:   2017年9月18日 下午1:56:47 
	 * @author: chengxg
	 */
	public Member selectByMobile(String mobile);
	
	/**
	 * 升级会员卡和权益
	 * @Title: updateMemCard   
	 * @param member
	 * @param card
	 * @date:   2017年9月18日 下午1:54:32 
	 * @author: chengxg
	 */
	public void upgradeMemberCard(Member member, Card card);
	
	/**
	 * 根据openid查询会员
	 * @Title: selectByMobile
	 * @param mobile
	 * @param storeNo
	 * @return
	 * @date:   2017年9月18日 下午1:56:47 
	 * @author: chengxg
	 */
	public Member selectByOpenid(String openid);
	
	/**
	 * 根据openid查询会员
	 * @Title: getWxUserByOpenId   
	 * @param openId
	 * @return
	 * @date:   2017年10月19日 上午10:45:24 
	 * @author: chengxg
	 */
	Map<String, Object> getWxUserByOpenId(@Param("openId") String openId);
	
	/**
	 * 保存新会员
	 * @Title: saveMember   
	 * @param userInfo
	 * @throws Exception
	 * @date:   2017年10月19日 下午12:37:35 
	 * @author: chengxg
	 */
	public void saveMember(UserInfo userInfo) throws Exception;
}
