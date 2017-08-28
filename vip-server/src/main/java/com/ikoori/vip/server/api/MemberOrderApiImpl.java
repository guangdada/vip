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
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.server.modular.biz.dao.CouponFetchDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.OrderDao;


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
    @Autowired	
    OrderDao orderDao;
	@Override
	public List<Map<String, Object>> getMemberOrderByOpenId(String openId) {
		Member member = memberDao.getMemberByOpenId(openId);
		if(member == null){
			return null;
		}
		List<Map<String,Object>> orders=orderDao.selectOrderListByMemberId(member.getId());
		if(orders==null){
			return null;
		}
		return orders;
	}
	@Override
	public List<Map<String, Object>> getMemberOrderDetailByOrderId(Long orderId) {
		List<Map<String,Object>> orderDetail=orderDao.selectOrderDetailListByOrderId(orderId);
		if (orderDetail == null) {
			return null;
		}
		return orderDetail;
	}

}
