package com.ikoori.vip.server.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ikoori.vip.api.service.CouponApi;
import com.ikoori.vip.common.constant.state.CouponUseState;
import com.ikoori.vip.common.persistence.dao.CardMapper;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.CouponFetch;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.common.util.RandomUtil;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.dao.CouponFetchDao;
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
	@Autowired
	CouponFetchDao couponFetchDao;
	@Autowired
	CouponMapper couponMapper;
	@Autowired
	CardMapper cardMapper;
	@Autowired
	MemberCardMapper memberCardMapper;
	
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
		// 判断是否满足领取条件
		checkCouponFetch(obj, coupon, member);

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
	 * 验证是否符合领取条件
	 * @Title: checkCouponFetch   
	 * @param obj
	 * @param coupon
	 * @param member
	 * @throws Exception
	 * @date:   2017年9月18日 下午5:15:39 
	 * @author: chengxg
	 */
	private void checkCouponFetch(JSONObject obj, Coupon coupon, Member member) throws Exception {
		// 优惠券领取会员等级限制
		Long limitCardId = coupon.getCardId();
		if (limitCardId != null) {
			/*List<Map<String, Object>> memberCard = memberCardDao.selectByMemberId(member.getId());
			if (memberCard == null || memberCard.size() == 0) {
				obj.put("msg", "您还没有会员卡哦");
				throw new Exception(obj.toJSONString());
			}
			String cardId = memberCard.get(0).get("cardId").toString();*/
			int count = getMemCardCountByCardId(member.getId(), limitCardId);
			if (count == 0) {
				Card card = cardMapper.selectById(limitCardId);
				obj.put("msg", "该优惠券只能“" + card.getName() + "”才能领哦");
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
		if (couponDao.updateStock(coupon.getId(),1) == 0) {
			obj.put("msg", "该优惠券已经领完啦");
			throw new Exception(obj.toJSONString());
		}
		// 领取人数更新
		couponDao.updateGetCountUser(coupon.getId(), member.getId());
	}
	
	/**
	 * 查询cardId类型会员卡的数量
	 * @Title: getMemCardCountByCardId   
	 * @param memberId
	 * @param cardId
	 * @return
	 * @date:   2017年9月18日 下午11:32:32 
	 * @author: chengxg
	 */
	public int getMemCardCountByCardId(Long memberId, Long cardId) {
		Wrapper<MemberCard> w = new EntityWrapper<MemberCard>();
		w.eq("member_id", memberId).eq("card_id", cardId).eq("status", 1);
		return memberCardMapper.selectCount(w);
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
	
	/**
	 * 根据券号，激活优惠券
	 * @Title: activeCoupon   
	 * @param verifyCode
	 * @param openId
	 * @date:   2017年9月18日 下午4:18:12 
	 * @author: chengxg
	 * @throws Exception 
	 */
	public JSONObject activeCoupon(String verifyCode, String openId) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("code", true);
		obj.put("msg", "领取成功");
		// openId获得会员
		// 判断会员卡是否过期
		Member member = memberDao.getMemberByOpenId(openId);
		if (member == null) {
			obj.put("msg", "您还不是会员哦");
			throw new Exception(obj.toJSONString());
		}
		if (!member.isIsActive() || StringUtils.isBlank(member.getMobile())) {
			obj.put("msg", "您还没有激活会员卡哦");
			throw new Exception(obj.toJSONString());
		}
		CouponFetch cf = couponFetchDao.selectByVerifyCode(verifyCode);
		if (cf == null) {
			obj.put("msg", "没有找到该优惠券哦");
			throw new Exception(obj.toJSONString());
		}
		if(cf.getMemberId() != null){
			obj.put("msg", "该优惠券已经被领取过了哦");
			throw new Exception(obj.toJSONString());
		}
		
		Coupon coupon = couponMapper.selectById(cf.getCouponId());
		if (coupon == null || !coupon.isIsExpired() || !coupon.isIsInvalid()) {
			obj.put("msg", "该优惠券已经过期啦");
			throw new Exception(obj.toJSONString());
		}
		
		// 判断是否符合领取条件
		checkCouponFetch(obj, coupon, member);

		//修改优惠券领取状态
		cf.setMemberId(member.getId());
		cf.setMerchantId(member.getMerchantId());
		cf.setIsInvalid(true);
		cf.setMobile(member.getMobile());
		cf.setVersion(cf.getVersion());
		cf.setWxUserId(member.getWxUserId());
		int count = couponFetchMapper.updateById(cf);
		if (count == 0) {
			obj.put("msg", "激活失败，未知错误");
			throw new Exception(obj.toJSONString());
		}
		return obj;

	}
}
