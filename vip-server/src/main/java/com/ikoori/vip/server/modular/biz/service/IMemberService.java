package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
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

	public Integer insert(Member member);
	
	public Member selecByMobile(String mobile);
	
	public void saveMember(Member member,Long cardId);
	public void deleteMember(Long memberId);

	List<Map<String, Object>> getMemberList(@Param("page") Page<Member> page, @Param("name") String name,
			@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
