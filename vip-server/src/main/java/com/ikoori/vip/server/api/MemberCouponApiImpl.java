package com.ikoori.vip.server.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikoori.vip.api.service.MemberCouponApi;
import com.ikoori.vip.common.persistence.dao.WxUserMapper;
import com.ikoori.vip.server.modular.biz.dao.CouponFetchDao;


@Service
public class MemberCouponApiImpl implements MemberCouponApi {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	WxUserMapper wxUserMapper;
	@Autowired
	CouponFetchDao couponFetchDao;
	
	/**   
	 * <p>Title: getMemberCouponByOpenId</p>   
	 * <p>Description:优惠券信息 </p>   
	 * @param openId
	 * @return   
	 * @see com.ikoori.vip.api.service.MemberCouponApi#getMemberCouponByOpenId(java.lang.String)   
	 */  
	@Override
	public List<Map<String, Object>> getMemberCouponByOpenId(String openId) {
		log.info("进入getMemberCouponByOpenId");
		log.info("进入getMemberCouponByOpenId>>openId=" + openId);
		List<Map<String, Object>> result=couponFetchDao.selectCoupon(openId);
	    if(result==null){
	    	log.info("优惠券信息为空");
	    	return null;
	    }
	    log.info("结束getMemberCouponByOpenId");
	    return result;
	}
	
	
	/**   
	 * <p>Title: getMemberCouponDetailByCouponId</p>   
	 * <p>Description:优惠券详细信息 </p>   
	 * @param couponId 优惠券id
	 * @param id       优惠券领取记录id
	 * @return   
	 * @see com.ikoori.vip.api.service.MemberCouponApi#getMemberCouponDetailByCouponId(java.lang.Long, java.lang.Long)   
	 */  
	@Override
	public Object getMemberCouponDetailByCouponId(Long couponId,Long id) {
		log.info("进入getMemberCouponDetailByCouponId");
		log.info("进入getMemberCouponDetailByCouponId>>couponId=" + couponId);
		log.info("进入getMemberCouponDetailByCouponId>>id=" + id);
		Object result=couponFetchDao.selectCouponDetail(couponId, id);
		if(result==null){
			log.info("优惠券详细信息为空");
			return null;
		}
		log.info("结束getMemberCouponDetailByCouponId");
		return result;
	}
	
}
