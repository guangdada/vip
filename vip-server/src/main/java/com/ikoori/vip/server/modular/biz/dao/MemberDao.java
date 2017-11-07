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
			@Param("memName") String memName, @Param("memSex") Integer memSex,@Param("memNickName") String memNickName,@Param("memMobile") String memMobile,@Param("cardId") Long cardId,@Param("cardNumber") String cardNumber,@Param("isActive") Integer isActive,@Param("unionid") String unionid,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
	
	Member getMemberByUnionid(@Param("unionid") String unionid);
	
	Member getMemberByOpenid(@Param("openid") String openid);
	
	Map<String, Object> getWxUserByUnionid(@Param("unionid") String unionid);
	
	Map<String, Object> getWxUserByOpenid(String openid);
	
	Member selectByMobile(@Param("mobile") String mobile);
	
	Member getMemberByMobile(@Param("mobile") String mobile);
	
	int updatePoint(@Param("memberId") Long memberId, @Param("point") int point);
	
	int updateMemberInfoByUnionid(@Param("unionid") String unionid,@Param("name")String name,@Param("mobile")String mobile,@Param("sex")int sex,@Param("address")String address,@Param("birthday")Date birthday,@Param("area")String area);
    
	Member selectByMemberId(@Param("memberId") Long memberId);
	
	void updateMemUnionid(@Param("unionid") String unionid,@Param("openid") String openid);
	
	void updateWxUnionid(@Param("unionid") String unionid,@Param("openid") String openid);
}
