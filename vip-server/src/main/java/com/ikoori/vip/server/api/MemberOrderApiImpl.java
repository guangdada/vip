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
	 * <p>Title: getMemberOrderByUnionid</p>   
	 * <p>Description: 获取会员订单</p>   
	 * @param unionid
	 * @return   
	 */  
	@Override
	public List<Map<String, Object>> getMemberOrderByUnionid(String unionid,int start,int pageSize) {
		log.info("进入getMemberOrderByUnionid>>unionid=" + unionid);
		Member member = memberDao.getMemberByUnionid(unionid);
		if (member == null) {
			log.info("member == null");
			return null;
		}
		List<Map<String, Object>> orders = orderDao.selectOrderListByMemberId(member.getId(),start,pageSize);
		if (orders == null) {
			log.info("orders==null");
			return null;
		}
		log.info("结束getMemberOrderByUnionid");
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
