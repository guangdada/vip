package com.ikoori.vip.server.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikoori.vip.api.service.MemberPointApi;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.PointDao;


@Service
public class MemberPointApiImpl implements MemberPointApi {
	@Autowired
	MemberDao memberDao;
	@Autowired
    PointDao pointDao;
	
	/**   
	 * <p>Title: getMemberPointByOpenId</p>   
	 * <p>Description:查询会员积分 </p>   
	 * @param openId
	 * @return   
	 * @see com.ikoori.vip.api.service.MemberPointApi#getMemberPointByOpenId(java.lang.String)   
	 */  
	@Override
	public List<Map<String, Object>> getMemberPointByOpenId(String openId) {
		Member member = memberDao.getMemberByOpenId(openId);
		if(member == null){
			return null;
		}
		List<Map<String, Object>> points= pointDao.selectPointListByMemberId(member.getId());
		return points;
	}

}
