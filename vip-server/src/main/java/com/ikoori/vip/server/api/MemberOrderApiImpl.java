package com.ikoori.vip.server.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikoori.vip.api.service.MemberOrderApi;
import com.ikoori.vip.common.persistence.dao.CardMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.MerchantMapper;
import com.ikoori.vip.common.persistence.dao.WxUserMapper;
import com.ikoori.vip.server.modular.biz.dao.CouponFetchDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;


@Service
public class MemberOrderApiImpl implements MemberOrderApi {
	@Autowired
	MemberCardMapper memberCardMapper;
	@Autowired
	MemberDao memberDao;
	@Autowired
	WxUserMapper wxUserMapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	CardMapper cardMapper;
	@Autowired
	MerchantMapper merchantMapper;
	@Autowired
	CouponFetchDao couponFetchDao;
	/*@Autowired
	CouponFetchWarpper couponFetchMapper;*/
	/*@Override
	public List<Map<String, Object>> getMemberCouponByOpenId(String openId) {
		Member member = memberDao.getMemberByOpenId(openId);
		if(member == null){
			return null;
		}
		List<Map<String, Object>> result=couponFetchDao.selectCoupon(member.getId());
	    if(result==null){
	    	return null;
	    }
	    return result;
	}
	//优惠券详细信息
	@Override
	public Object getMemberCouponDetailByCouponId(Long couponId,Long id) {
		// TODO Auto-generated method stub
		Object result=couponFetchDao.selectCouponDetail(couponId, id);
		if(result==null){
			return null;
		}
		return result;
	}
	*/
	@Override
	public List<Map<String, Object>> getMemberOrderByOpenId(String openId) {
		// TODO Auto-generated method stub
		return null;
	}

}
