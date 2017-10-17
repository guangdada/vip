package com.ikoori.vip.server.api;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ikoori.vip.api.service.CouponApi;
import com.ikoori.vip.common.constant.state.CouponCodeStatus;
import com.ikoori.vip.common.persistence.dao.CardMapper;
import com.ikoori.vip.common.persistence.dao.CouponCodeMapper;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.CouponCode;
import com.ikoori.vip.common.persistence.model.CouponFetch;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.dao.CouponFetchDao;
import com.ikoori.vip.server.modular.biz.dao.MemberCardDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.service.ICouponFetchService;

/**
 * 优惠券领取接口
 * 
 * @ClassName: CouponApiImpl
 * @author: chengxg
 * @date: 2017年9月14日 下午10:56:36
 */
@Service
public class CouponApiImpl implements CouponApi {
	private Logger log = LoggerFactory.getLogger(this.getClass());
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
	@Autowired
	ICouponFetchService couponFetchService;
	@Autowired
	CouponCodeMapper couponCodeMapper;

	/**
	 * 根据优惠券别名查询优惠券
	 * 
	 * @Title: getCouponByAlias
	 * @param alias
	 * @return
	 * @date: 2017年9月15日 上午11:20:05
	 * @author: chengxg
	 */
	public Object getCouponByAlias(String alias) {
		return couponDao.getCouponByAlias(alias);
	}

	/**
	 * 根据券码是否存在
	 * 
	 * @Title: getCouponCode
	 * @param verifyCode
	 * @return
	 * @date: 2017年10月17日 下午2:46:22
	 * @author: chengxg
	 */
	public CouponCode getCouponCode(String verifyCode) {
		Wrapper<CouponCode> w = new EntityWrapper<>();
		w.eq("verify_code", verifyCode);
		w.eq("status", 1);
		List<CouponCode> codes = couponCodeMapper.selectList(w);
		if (CollectionUtils.isNotEmpty(codes)) {
			return codes.get(0);
		}
		return null;
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
	@Transactional(readOnly = false)
	@Override
	public JSONObject getCoupon(String alias, String openId) throws Exception {
		log.info("进入getCoupon");
		log.info("进入getCoupon >> alias=" + alias);
		log.info("进入getCoupon >> openId=" + openId);
		JSONObject obj = new JSONObject();
		obj.put("code", true);
		obj.put("msg", "领取成功");
		// 根据alia获得优惠券
		// 判断优惠券是否有效
		Coupon coupon = couponDao.getCouponByAlias(alias);
		if (coupon == null || !coupon.isIsExpired() || !coupon.isIsInvalid()) {
			log.error("该优惠券已经过期啦");
			obj.put("msg", "该优惠券已经过期啦");
			throw new Exception(obj.toJSONString());
		}
		// openId获得会员
		// 判断会员卡是否过期
		Member member = memberDao.getMemberByOpenId(openId);
		if (member == null) {
			log.error("您还不是会员哦");
			obj.put("msg", "您还不是会员哦");
			throw new Exception(obj.toJSONString());
		}
		// 判断是否满足领取条件
		checkCouponFetch(obj, coupon, member, 1);

		// 保存领取记录
		couponFetchService.saveCouponFetch(member, coupon, null);
		log.info("结束getCoupon");
		return obj;
	}

	/**
	 * 验证是否符合领取条件
	 * 
	 * @Title: checkCouponFetch
	 * @param obj
	 * @param coupon
	 * @param member
	 * @param count
	 * @throws Exception
	 * @date: 2017年9月18日 下午5:15:39
	 * @author: chengxg
	 */
	private void checkCouponFetch(JSONObject obj, Coupon coupon, Member member, Integer count) throws Exception {
		log.info("进入checkCouponFetch");
		// 优惠券领取会员等级限制
		Long limitCardId = coupon.getCardId();
		if (limitCardId != null) {
			int c = getMemCardCountByCardId(member.getId(), limitCardId);
			if (c == 0) {
				Card card = cardMapper.selectById(limitCardId);
				log.error("该优惠券只能“" + card.getName() + "”才能领哦");
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
				log.error("该优惠券每人只能领" + quota + "张哦");
				obj.put("msg", "该优惠券每人只能领" + quota + "张哦");
				throw new Exception(obj.toJSONString());
			}
		}
		// 更新优惠券库存和领取次数
		if (count != 0 && couponDao.updateStock(coupon.getId(), 1) == 0) {
			log.error("该优惠券已经领完啦");
			obj.put("msg", "该优惠券已经领完啦");
			throw new Exception(obj.toJSONString());
		}
		// 领取人数更新
		couponDao.updateGetCountUser(coupon.getId(), member.getId());
		log.info("结束checkCouponFetch");
	}

	/**
	 * 查询cardId类型会员卡的数量
	 * 
	 * @Title: getMemCardCountByCardId
	 * @param memberId
	 * @param cardId
	 * @return
	 * @date: 2017年9月18日 下午11:32:32
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
	 * 
	 * @Title: activeCoupon
	 * @param verifyCode
	 * @param openId
	 * @date: 2017年9月18日 下午4:18:12
	 * @author: chengxg
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public JSONObject activeCoupon(String verifyCode, String openId) throws Exception {
		log.info("进入activeCoupon");
		log.info("进入activeCoupon>>verifyCode=" + verifyCode);
		log.info("进入activeCoupon>>openId=" + openId);
		JSONObject obj = new JSONObject();
		obj.put("code", true);
		obj.put("msg", "领取成功");
		// openId获得会员
		// 判断会员卡是否过期
		Member member = memberDao.getMemberByOpenId(openId);
		if (member == null) {
			log.error("您还不是会员哦");
			obj.put("msg", "您还不是会员哦");
			throw new Exception(obj.toJSONString());
		}
		if (!member.isIsActive() || StringUtils.isBlank(member.getMobile())) {
			log.error("您还没有激活会员卡哦");
			obj.put("msg", "您还没有激活会员卡哦");
			throw new Exception(obj.toJSONString());
		}

		CouponCode couponCode = getCouponCode(verifyCode);
		if (couponCode == null) {
			log.error("没有找到该现金券哦" + verifyCode);
			obj.put("msg", "没有找到该现金券哦");
			throw new Exception(obj.toJSONString());
		}

		if (couponCode.getUseStatus() != CouponCodeStatus.publish.getCode()) {
			log.error("该现金券还没有发行哦");
			obj.put("msg", "该现金券还没有发行哦");
			throw new Exception(obj.toJSONString());
		}

		Coupon coupon = couponMapper.selectById(couponCode.getCouponId());
		if (coupon == null || !coupon.isIsExpired() || !coupon.isIsInvalid()) {
			log.error("该现金券已经过期啦");
			obj.put("msg", "该现金券已经过期啦");
			throw new Exception(obj.toJSONString());
		}

		// 判断是否符合领取条件
		checkCouponFetch(obj, coupon, member, 1);

		// 保存领取记录
		couponFetchService.saveCouponFetch(member, coupon, couponCode.getVerifyCode());
		
		// 修改券码状态为“已激活”
		couponCode.setUseStatus(CouponCodeStatus.active.getCode());
		couponCodeMapper.updateById(couponCode);
		log.info("结束activeCoupon");
		return obj;

	}
}
