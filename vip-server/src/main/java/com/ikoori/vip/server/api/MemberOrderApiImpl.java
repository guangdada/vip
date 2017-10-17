package com.ikoori.vip.server.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikoori.vip.api.service.MemberOrderApi;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.OrderDao;


@Service
public class MemberOrderApiImpl implements MemberOrderApi {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	MemberDao memberDao;
	@Autowired
	OrderDao orderDao;
    
	/**   
	 * <p>Title: getMemberOrderByOpenId</p>   
	 * <p>Description: 获取会员订单</p>   
	 * @param openId
	 * @return   
	 */  
	@Override
	public List<Map<String, Object>> getMemberOrderByOpenId(String openId) {
		log.info("进入getMemberOrderByOpenId");
		log.info("进入getMemberOrderByOpenId>>openId=" + openId);
		Member member = memberDao.getMemberByOpenId(openId);
		if (member == null) {
			log.info("member == null");
			return null;
		}
		List<Map<String, Object>> orders = orderDao.selectOrderListByMemberId(member.getId());
		if (orders == null) {
			log.info("orders==null");
			return null;
		}
		log.info("结束getMemberOrderByOpenId");
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
		log.info("进入getMemberOrderDetailByOrderId");
		log.info("进入getMemberOrderDetailByOrderId>>orderId=" + orderId);
		List<Map<String, Object>> orderDetail = orderDao.selectOrderDetailListByOrderId(orderId);
		if (orderDetail == null) {
			log.info("orderDetail == null");
			return null;
		}
		log.info("结束getMemberOrderDetailByOrderId");
		return orderDetail;
	}

}
