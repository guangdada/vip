package com.ikoori.vip.server.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ikoori.vip.api.service.CouponApi;
import com.ikoori.vip.common.constant.state.CouponUseState;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.CouponFetch;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.util.RandomUtil;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.dao.MemberCardDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;

/**
 * 优惠券领取接口
 * 
 * @ClassName: CouponApiImpl
 * @author: chengxg
 * @date: 2017年9月14日 下午10:56:36
 */
@Service
public class CouponApiImpl implements CouponApi {
	@Autowired
	CouponDao couponDao;
	@Autowired
	CouponFetchMapper couponFetchMapper;
	@Autowired
	MemberDao memberDao;
	@Autowired
	MemberCardDao memberCardDao;
	
	/**
	 * 根据优惠券别名查询优惠券
	 * @Title: getCouponByAlias   
	 * @param alias
	 * @return
	 * @date:   2017年9月15日 上午11:20:05 
	 * @author: chengxg
	 */
	public Object getCouponByAlias(String alias){
		return couponDao.getCouponByAlias(alias);
	}

	/**
	 * 领取优惠券
	 * 
	 * @Title: getStoreDetail
	 * @param alias
	 * @param openId
	 * @return
	 * @date: 2017年9月14日 下午10:59:05
	 * @author: chengxg
	 * @throws Exception 
	 */
	@Override
	public JSONObject getCoupon(String alias, String openId) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("code", true);
		obj.put("msg", "领取成功");
		// 根据alia获得优惠券
		// 判断优惠券是否有效
		Coupon coupon = couponDao.getCouponByAlias(alias);
		if (coupon == null || !coupon.isIsExpired() || !coupon.isIsInvalid()) {
			obj.put("msg", "该优惠券已经过期啦");
			throw new Exception(obj.toJSONString());
		}
		// openId获得会员
		// 判断会员卡是否过期
		Member member = memberDao.getMemberByOpenId(openId);
		if (member == null) {
			obj.put("msg", "您还不是会员哦");
			throw new Exception(obj.toJSONString());
		}
		// 优惠券领取会员等级限制
		Long limitCardId = coupon.getCardId();
		if (limitCardId != null) {
			List<Map<String, Object>> memberCard = memberCardDao.selectByMemberId(member.getId());
			if (memberCard == null || memberCard.size() == 0) {
				obj.put("msg", "您还没有会员卡哦");
				throw new Exception(obj.toJSONString());
			}
			String cardId = memberCard.get(0).get("cardId").toString();
			String cardName = memberCard.get(0).get("name").toString();
			if (!limitCardId.toString().equals(cardId)) {
				obj.put("msg", "该优惠券只能“" + cardName + "”才能领哦");
				throw new Exception(obj.toJSONString());
			}
		}
		// 优惠券每人限领数量判断
		Integer quota = coupon.getQuota();
		if (quota != null) {
			// 获得当前会员已经领取的数量
			Integer getCount = getMemGetCount(coupon.getId(), member.getId());
			if (getCount >= quota) {
				obj.put("msg", "该优惠券每人只能领" + quota + "张哦");
				throw new Exception(obj.toJSONString());
			}
		}
		// 更新优惠券库存和领取次数
		if (couponDao.updateStock(coupon.getId()) == 0) {
			obj.put("msg", "该优惠券已经领完啦");
			throw new Exception(obj.toJSONString());
		}
		// 领取人数更新
		couponDao.updateGetCountUser(coupon.getId(), member.getId());

		// 生成领取记录
		CouponFetch couponFetch = new CouponFetch();
		couponFetch.setMemberId(member.getId());
		couponFetch.setCouponId(coupon.getId());
		couponFetch.setAvailableValue(coupon.getOriginValue());
		couponFetch.setExpireTime(coupon.getEndAt());
		couponFetch.setValidTime(coupon.getStartAt());
		couponFetch.setIsInvalid(true);
		couponFetch.setIsUsed(CouponUseState.NO_USED.getCode());
		couponFetch.setMerchantId(coupon.getMerchantId());
		couponFetch.setMessage("谢谢关注！");
		couponFetch.setVerifyCode(RandomUtil.generateCouponCode());
		couponFetch.setValue(coupon.getOriginValue());
		couponFetch.setStoreId(coupon.getStoreId());
		couponFetch.setUsedValue(0);
		couponFetchMapper.insert(couponFetch);
		return obj;
	}

	/**
	 * 获得当前会员领取优惠券的张数
	 * 
	 * @Title: getMemGetCount
	 * @param couponId
	 * @param memberId
	 * @return
	 * @date: 2017年9月15日 上午10:41:25
	 * @author: chengxg
	 */
	public int getMemGetCount(Long couponId, Long memberId) {
		Wrapper<CouponFetch> wrapper = new EntityWrapper<CouponFetch>();
		wrapper.eq("coupon_id", couponId);
		wrapper.eq("member_id", memberId);
		wrapper.eq("status", 1);
		return couponFetchMapper.selectCount(wrapper);
	}
}
