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
	List<Map<String, Object>> getMemberList(@Param("page") Page<Member> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

	Member getMemberByOpenId(@Param("openId") String openId);
	
	Member selectByMobileAndStoreNo(@Param("mobile") String mobile,@Param("storeNo") String storeNo);
	Member getMemberByMobile(@Param("mobile") String mobile);
	int updatePoint(Long memberId, int point , int version);
	int updateMemberInfoByOpenId(@Param("openId") String openId,@Param("name")String name,@Param("mobile")String mobile,@Param("sex")int sex,@Param("address")String address,@Param("birthday")Date birthday);
}
