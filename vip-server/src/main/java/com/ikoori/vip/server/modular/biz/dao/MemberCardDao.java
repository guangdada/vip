package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.MemberCard;

/**
 * 领取记录Dao
 *
 * @author chengxg
 * @Date 2017-08-14 13:14:55
 */
public interface MemberCardDao {
	List<Map<String, Object>> getMemberCardList(@Param("cardId") Long cardId, @Param("grantType") Integer grantType,
			@Param("state") Integer state, @Param("mobile") String mobile, @Param("page") Page<MemberCard> page,
			@Param("nickname") String nickname, @Param("orderByField") String orderByField,
			@Param("isAsc") boolean isAsc, @Param("merchantId") Long merchantId,
			@Param("cardNumber") String cardNumber);
   
   MemberCard getMemberCard(@Param("memberId") Long memberId);
   
   List<Map<String,Object>> selectByMemberId(@Param("memberId") Long memberId);
   
   int updateDefaultCard(@Param("memberId") Long memberId,@Param("cardId") Long cardId);
}
