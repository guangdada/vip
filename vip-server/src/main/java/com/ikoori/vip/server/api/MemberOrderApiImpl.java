package com.ikoori.vip.server.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikoori.vip.api.service.MemberOrderApi;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.OrderDao;


@Service
public class MemberOrderApiImpl implements MemberOrderApi {
	@Autowired
	MemberDao memberDao;
    @Autowired	
    OrderDao orderDao;
    
	/**   
	 * <p>Title: getMemberOrderByOpenId</p>   
	 * <p>Description: 获取会员订单</p>   
	 * @param openId
	 * @return   
	 * @see com.ikoori.vip.api.service.MemberOrderApi#getMemberOrderByOpenId(java.lang.String)   
	 */  
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
	
	
	/**   
	 * <p>Title: getMemberOrderDetailByOrderId</p>   
	 * <p>Description:获取会员订单详情 </p>   
	 * @param orderId
	 * @return   
	 * @see com.ikoori.vip.api.service.MemberOrderApi#getMemberOrderDetailByOrderId(java.lang.Long)   
	 */  
	@Override
	public List<Map<String, Object>> getMemberOrderDetailByOrderId(Long orderId) {
		List<Map<String,Object>> orderDetail=orderDao.selectOrderDetailListByOrderId(orderId);
		if (orderDetail == null) {
			return null;
		}
		return orderDetail;
	}

}
