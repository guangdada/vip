package com.ikoori.vip.server.modular.biz.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Member;

/**
 * 会员Dao
 *
 * @author chengxg
 * @Date 2017-08-02 12:31:42
 */
public interface MemberDao {
	List<Map<String, Object>> getMemberList(@Param("page") Page<Map<String, Object>> page,
			@Param("memName") String memName, @Param("memSex") Integer memSex,@Param("memNickName") String memNickName,@Param("memMobile") String memMobile,@Param("cardId") Long cardId,@Param("cardNumber") String cardNumber,@Param("isActive") Integer isActive,@Param("openId") String openId,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
	
	Member getMemberByOpenId(@Param("openId") String openId);
	
	Object getWxUserByOpenId(@Param("openId") String openId);
	
	Member selectByMobile(@Param("mobile") String mobile);
	
	Member getMemberByMobile(@Param("mobile") String mobile);
	
	int updatePoint(@Param("memberId") Long memberId, @Param("point") int point);
	
	int updateMemberInfoByOpenId(@Param("openId") String openId,@Param("name")String name,@Param("mobile")String mobile,@Param("sex")int sex,@Param("address")String address,@Param("birthday")Date birthday,@Param("area")String area);
    
	Member selectByMemberId(@Param("memberId") Long memberId);
}
