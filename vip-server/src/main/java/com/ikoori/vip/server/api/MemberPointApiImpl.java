package com.ikoori.vip.server.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikoori.vip.api.service.MemberPointApi;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.PointDao;


@Service
public class MemberPointApiImpl implements MemberPointApi {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	MemberDao memberDao;
	@Autowired
	PointDao pointDao;
	
	/**   
	 * <p>Title: getMemberPointByUnionid</p>   
	 * <p>Description:查询会员积分 </p>   
	 * @param unionid
	 * @return   
	 * @see com.ikoori.vip.api.service.MemberPointApi#getMemberPointByUnionid(java.lang.String)   
	 */  
	@Override
	public List<Map<String, Object>> getMemberPointByUnionid(String unionid,int start,int pageSize) {
		log.info("进入getMemberPointByUnionid>>unionid=" + unionid);
		List<Map<String, Object>> points = pointDao.selectPointListByMemberId(unionid,start,pageSize);
		return points;
	}

}
