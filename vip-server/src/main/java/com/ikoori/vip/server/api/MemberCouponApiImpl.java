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
	 * <p>Title: getMemberCouponByUnionid</p>   
	 * <p>Description:优惠券信息 </p>   
	 * @param unionid
	 * @return   
	 * @see com.ikoori.vip.api.service.MemberCouponApi#getMemberCouponByUnionid(java.lang.String)   
	 */  
	@Override
	public List<Map<String, Object>> getMemberCouponByUnionid(String unionid) {
		log.info("进入getMemberCouponByUnionid>>unionid=" + unionid);
		List<Map<String, Object>> result=couponFetchDao.selectCoupon(unionid);
	    if(result==null){
	    	log.info("优惠券信息为空");
	    	return null;
	    }
	    log.info("结束getMemberCouponByUnionid");
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
