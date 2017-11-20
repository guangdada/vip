package com.ikoori.vip.api.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 优惠券领取接口
 * @ClassName:  CouponApi
 * @author: chengxg
 * @date:   2017年9月14日 下午10:57:03
 */
public interface CouponApi {
	/**
	 * 根据优惠券别名查询优惠券
	 * @Title: getCouponByAlias   
	 * @param alias
	 * @return
	 * @date:   2017年9月15日 上午11:19:28 
	 * @author: chengxg
	 */
	public Object getCouponByAlias(String alias);
	
	/**
	 * 领取优惠券
	 * @Title: fetchCoupon   
	 * @param alias
	 * @param unionid
	 * @return
	 * @date:   2017年9月14日 下午10:59:29 
	 * @author: chengxg
	 */
	public JSONObject fetchCoupon(String couponId,String unionid) throws Exception;
	
	/**
	 * 根据券号激活优惠券
	 * @Title: activeCoupon   
	 * @param verifyCode
	 * @param unionid
	 * @return
	 * @throws Exception
	 * @date:   2017年9月18日 下午5:38:19 
	 * @author: chengxg
	 */
	public JSONObject activeCoupon(String verifyCode, String unionid) throws Exception;
}
