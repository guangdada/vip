package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.constant.state.CardGrantType;
import com.ikoori.vip.common.constant.state.CardTermsType;
import com.ikoori.vip.common.constant.state.MemCardState;
import com.ikoori.vip.common.constant.state.MemSourceType;
import com.ikoori.vip.common.constant.state.PointTradeType;
import com.ikoori.vip.common.constant.state.RightType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.CardMapper;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.PointTradeMapper;
import com.ikoori.vip.common.persistence.dao.WxUserMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.CardRight;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Point;
import com.ikoori.vip.common.persistence.model.WxUser;
import com.ikoori.vip.common.support.HttpUtil;
import com.ikoori.vip.common.util.DateUtil;
import com.ikoori.vip.common.util.RandomUtil;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.core.util.WeChatAPI;
import com.ikoori.vip.server.modular.biz.dao.CardDao;
import com.ikoori.vip.server.modular.biz.dao.CardRightDao;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.dao.MemberCardDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.OrderDao;
import com.ikoori.vip.server.modular.biz.dao.PointDao;
import com.ikoori.vip.server.modular.biz.service.ICouponFetchService;
import com.ikoori.vip.server.modular.biz.service.IMemberService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IPointTradeService;

/**
 * 会员Dao
 *
 * @author chengxg
 * @Date 2017-08-02 12:31:42
 */
@Service
public class MemberServiceImpl implements IMemberService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	GunsProperties gunsProperties;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	MemberDao memberDao;
	@Autowired
	MemberCardMapper memberCardMapper;
    @Autowired
	IMerchantService merchantService;
    @Autowired
    PointTradeMapper pointTradeMapper;
    @Autowired
    CardMapper cardMapper;
    @Autowired
	CardRightDao cardRightDao;
    @Autowired
	CouponMapper couponMapper;
    @Autowired
	CouponDao couponDao;
    @Autowired
	CouponFetchMapper couponFetchMapper;
    @Autowired
	MemberCardDao memberCardDao;
    @Autowired
	IPointTradeService pointTradeService;
    @Autowired
    ICouponFetchService couponFetchService;
    @Autowired
	WxUserMapper wxUserMapper;
    @Autowired
	CardDao cardDao;
    @Autowired
	PointDao pointDao;
    @Autowired
    OrderDao orderDao;
	@Override
	public Integer deleteById(Long id) {
		return memberMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Member member) {
		return memberMapper.updateById(member);
	}

	@Override
	public Member selectById(Long id) {
		return memberMapper.selectById(id);
	}
	

	@Override
	public Integer insert(Member member) {
		return memberMapper.insert(member);
	}


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveMember(Member member, Long cardId) {
		//当前登录账号
    	Long createUserId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(createUserId);
    	/*member.setIsActive(false);*/
    	this.insert(member);
    	
    	// 获得会员卡
    	Card card = cardMapper.selectById(cardId);
    	
    	//会员卡
		MemberCard mc=new MemberCard();
        mc.setMemberId(member.getId());
        mc.setMerchantId(merchant.getId());
        mc.setCardId(cardId);
        mc.setCardNumber(RandomUtil.generateCardNum(card.getCardNumberPrefix()));
        memberCardMapper.insert(mc);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteMember(Long memberId) {
		Member member=this.selectById(memberId);
		MemberCard mc=new MemberCard();
	    mc.setMemberId(member.getId());
		mc=memberCardMapper.selectOne(mc);
		memberCardMapper.deleteById(mc.getId());
		this.deleteById(memberId);
	}

	/**   
	 * <p>Title: updateMember</p>   
	 * <p>Description: 修改会员</p>   
	 * @param member
	 * @param cardId 会员卡号
	 * @param point  交易积分
	 * @see com.ikoori.vip.server.modular.biz.service.IMemberService#updateMember(com.ikoori.vip.common.persistence.model.Member, java.lang.Long, int)   
	 */  
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateMember(Member member, Long cardId, int point) {
		Member dbMember = selectById(member.getId());
		// 会员没激活，填手机号码，判断手机号码是否唯一
		if (!dbMember.isIsActive()) {
			// 修改会员， 判断账号是否重复
			Member memberRe = selectByMobile(member.getMobile());
			if (memberRe != null) {
				throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
			}
		}

		// 更新交易积分
		if (point != 0) {
			Boolean inOut = point > 0 ? true : false;
			pointTradeService.savePointTrade(inOut, PointTradeType.GIVE.getCode(), point, dbMember.getId(), null,
					dbMember.getMerchantId(), null, "");
		}

		// 更新会员信息
		member.setPoints(member.getPoints()+point);
		member.setIsActive(true);
		memberMapper.updateById(member);

		// 更新会员卡
		Card card = cardMapper.selectById(cardId);
		upgradeMemberCard(dbMember, card);
	}
	
	public Member selectByMobile(String mobile){
		return memberDao.selectByMobile(mobile);
	}

	/**   
	 * <p>Title: getMemberList</p>   
	 * <p>Description: 按条件查找会员</p>   
	 * @param page
	 * @param memName 会员姓名
	 * @param memSex 会员性别
	 * @param memNickName 会员昵称
	 * @param memMobile 会员手机号
	 * @param orderByField
	 * @param isAsc
	 * @return   
	 * @see com.ikoori.vip.server.modular.biz.service.IMemberService#getMemberList(com.baomidou.mybatisplus.plugins.Page, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, boolean)   
	 */  
	@Override
	public List<Map<String, Object>> getMemberList(Page<Map<String, Object>> page, String memName, Integer memSex,
			String memNickName, String memMobile,Long cardId,String cardNumber,Integer isActive,String unionid,String orderByField, boolean isAsc) {
		return memberDao.getMemberList(page, memName, memSex, memNickName, memMobile, cardId,cardNumber,isActive,unionid,orderByField, isAsc);
	}

	@Override
	public Member selectByMemberId(Long memberId) {
		return memberDao.selectByMemberId(memberId);
	}
	
	/**
	 * 将会员除了cardId类别的会员卡都置为非默认状态
	 * @param cardId
	 * @param memberId
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int updateDefaultCard(Long cardId,Long memberId){
		return memberCardDao.updateDefaultCard(memberId, cardId);
	}
	
	/**
	 * 获得会员指定类别的会员卡
	 * @param memberId
	 * @param cardId
	 * @return
	 */
	public MemberCard getMemberCard(Long memberId,Long cardId){
		Wrapper<MemberCard> w = new EntityWrapper<MemberCard>();
		w.eq("member_id", memberId).eq("card_id", cardId);
		List<MemberCard> mcs = memberCardMapper.selectList(w);
		if(CollectionUtils.isNotEmpty(mcs)){
			return mcs.get(0);
		}
		return null;
	}
	
	
	/**
	 * 初始化微信用户信息
	 * 
	 * @Title: setWxUserInfo
	 * @param userInfo
	 * @param wxUser
	 * @date: 2017年9月18日 上午12:25:58
	 * @author: chengxg
	 */
	private void setWxUserInfo(UserInfo userInfo, WxUser wxUser) {
		log.info("进入setWxUserInfo");
		wxUser.setCity(userInfo.getCity());
		wxUser.setCountry(userInfo.getCountry());
		wxUser.setHeadimgurl(userInfo.getHeadimgurl());
		wxUser.setSubscribe(userInfo.getSubscribe());
		wxUser.setLanguage(userInfo.getLanguage());
		wxUser.setNickname(userInfo.getNickname());
		wxUser.setOpenid(userInfo.getOpenid());
		wxUser.setProvince(userInfo.getProvince());
		wxUser.setRemark(userInfo.getRemark());
		wxUser.setSex(userInfo.getSex());
		wxUser.setUnionid(userInfo.getUnionid());
		wxUser.setSubscribeTime(new Date());
		log.info("结束setWxUserInfo");
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveMember(UserInfo userInfo,boolean update) throws Exception {
		log.info("进去saveMember>>userInfo:" + userInfo.toString());
		if (userInfo.getUnionid() == null) {
			log.info("微信用户unionid为空");
			throw new Exception("微信用户unionid为空");
		}
		synchronized (userInfo.getUnionid().intern()) {
			Member member = memberDao.getMemberByUnionid(userInfo.getUnionid());
			if (member == null) {
				// 保存微信用户
				log.info("保存微信用户");
				WxUser wxUser = new WxUser();
				setWxUserInfo(userInfo, wxUser);
				wxUserMapper.insert(wxUser);

				// 保存会员
				log.info("保存会员信息");
				member = new Member();
				member.setUnionid(wxUser.getUnionid());
				member.setIsActive(false);
				// 需要根据appid获得商户id
				member.setMerchantId(gunsProperties.getMerchantId());
				member.setPoints(0);
				member.setSourceType(MemSourceType.wechat.getCode());
				memberMapper.insert(member);

				// 查询获取类型为“关注微信”的会员卡
				log.info("查询获取类型为“关注微信”的会员卡");
				Card card = cardDao.getCardByGrantTypeAndMerchantId(gunsProperties.getMerchantId(),
						CardGrantType.SUB_WX.getCode());
				if (card == null) {
					log.info("没有找到会员卡类型");
					// obj.put("msg", "没有找到会员卡类型");
					// throw new Exception(obj.toJSONString());
					throw new Exception("没有找到会员卡类型");
				}

				log.info("保存会员卡领取记录");
				upgradeMemberCard(member, card);

				// 关注微信返回积分
				Point point = pointDao.getSubscribeWx(member.getMerchantId());
				if (point != null) {
					pointTradeService.savePointTrade(true, PointTradeType.SUBSCRIBE_WX.getCode(), point.getPoints(),
							member.getId(), point.getId(), member.getMerchantId(), null, "");
				}
			} else {
				// 小程序入口，不要更新openid
				if(update){
					log.info("用户已经存在，开始更新微信账号信息");
					WxUser wxUser = new WxUser();
					wxUser.setUnionid(userInfo.getUnionid());
					wxUser = wxUserMapper.selectOne(wxUser);
					setWxUserInfo(userInfo, wxUser);
					wxUserMapper.updateById(wxUser);
				}
			}
		}
	}
	
	/**
	 * 升级会员卡和权益
	 * @Title: upgradeMemCard   
	 * @param member
	 * @param card
	 * @date:   2017年9月18日 下午1:51:33 
	 * @author: chengxg
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void upgradeMemberCard(Member member, Card card) {
		MemberCard mc = getMemberCard(member.getId(), card.getId());
		if (mc != null) {
			log.info("已经有该级别会员卡");
			// 已经有该级别会员卡
			mc.setIsDefault(true);
			memberCardMapper.updateById(mc);
		} else {
			log.info("没有该级别会员卡,发送一张");
			// 没有该级别会员卡
			MemberCard memCard = new MemberCard();
			memCard.setCardId(card.getId());
			memCard.setCardNumber(RandomUtil.generateCardNum(card.getCardNumberPrefix()));
			memCard.setMemberId(member.getId());
			memCard.setMerchantId(member.getMerchantId());
			memCard.setState(MemCardState.USED.getCode());
			memCard.setIsDefault(true);
			memberCardMapper.insert(memCard);
			
			// 获取会员卡权益
			log.info("获取会员卡权益");
			List<CardRight> cardRights = cardRightDao.selectByCardId(card.getId());
			if (CollectionUtils.isNotEmpty(cardRights)) {
				for (CardRight cardRight : cardRights) {
					if (RightType.COUPON.getCode().equals(cardRight.getRightType())) {
						log.info("赠送id=" + cardRight.getCouponId() + "的优惠券");
						Coupon coupon = couponMapper.selectById(cardRight.getCouponId());
						Integer number = cardRight.getNumber();
						log.info("赠送数量为：" + number);
						log.info("剩余数量为：" + coupon.getStock());
						if (number != null) {
							int us = couponDao.updateStock(coupon.getId(), number); // 更新库存数
							if (us == 0) {
								log.info("修改库存失败");
								continue;
							}
							log.info("保存优惠券领取记录");
							for (int i = 0; i < number.intValue(); i++) {
								// 保存领取记录
								couponFetchService.saveCouponFetch(member, coupon,null);
							}
							int uu = couponDao.updateGetCountUser(coupon.getId(), member.getId()); // 跟新领取人数
							if (uu == 0) {
								log.info("修改领取人数失败");
								continue;
							}
						}
					} else if (RightType.POINTS.getCode().equals(cardRight.getRightType())) {
						log.info("赠送积分:" + cardRight.getPoints());
						pointTradeService.savePointTrade(true, PointTradeType.CARD.getCode(), cardRight.getPoints(),
								member.getId(), null, member.getMerchantId(), null, "");
					}
				}
			}
			// 获得关注微信的积分规则
		}
		// 修改会员的默认会员卡
		log.info("修改会员的默认会员卡");
		updateDefaultCard(card.getId(),member.getId());
	}

	/**
	 * 根据unionid查询会员
	 * @Title: selectByUnionid   
	 * @param unionid
	 * @return
	 * @date:   2017年10月19日 上午10:19:25 
	 * @author: chengxg
	 */
	@Override
	public Member selectByUnionid(String unionid) {
		Member member = memberDao.getMemberByUnionid(unionid);
		return member;
	}

	@Override
	public Map<String, Object> getWxUserByUnionid(String unionid) {
		Map<String, Object> member = memberDao.getWxUserByUnionid(unionid);
		return member;
	}
	
	@Override
	public Map<String, Object> getWxUserByOpenid(String openid) {
		Map<String, Object> member = memberDao.getWxUserByOpenid(openid);
		return member;
	}
	
	
	public void updateUnionid() {
		String url = WeChatAPI.batchget + "?access_token=" + WeChatAPI.getAccesstoken();
		boolean c = true;
		List<Member> members = null;
		do {
			Wrapper<Member> w = new EntityWrapper<>();
			w.isNull("unionid");
			w.last(" limit 100");
			members = memberMapper.selectList(w);
			if (members != null && members.size() > 0) {
				JSONObject data = new JSONObject();
				JSONArray o = new JSONArray();
				for(Member m : members){
					JSONObject sobj = new JSONObject();
					sobj.put("openid", m.getOpenId());
					sobj.put("lang", "zh_CN");
					o.add(sobj);
				}
				data.put("user_list", o);
				updateUnionid(url, data.toJSONString());
				System.out.println(data.toJSONString());
			}
			c = members == null || members.size() < 100 ? false : true;
			members = null;
		} while (c);
	}
	
	public void updateUnionid(String url , String data){
		try {
			Thread.sleep(2000);
			String result = HttpUtil.wpost(url, data);
			System.out.println(result);
			JSONObject obj = JSONObject.parseObject(result);
			JSONArray users = (JSONArray) obj.get("user_info_list");
			for (int i = 0; i < users.size(); i++) {
				try {
					JSONObject dd  = (JSONObject) users.get(i);
					String unionid = dd.getString("unionid");
					String openid = dd.getString("openid");
					memberDao.updateMemUnionid(unionid, openid);
					memberDao.updateWxUnionid(unionid, openid);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public JSONObject initCard(Long memberId) {
		// 获得会员的默认会员卡
		Map<String, Object> defaultCard = memberCardDao.selectByMemberId(memberId);
		if (defaultCard == null) {
			log.info("没有找到会员卡>>memberId=" + memberId);
			return null;
		}
		
		JSONObject obj = new JSONObject();
		obj.put("cardNumber", defaultCard.get("cardNumber"));
		obj.put("name", defaultCard.get("name"));
		obj.put("description", defaultCard.get("description"));
		obj.put("state", MemCardState.USED.getCode());
		// 判断是否生效或过期
		Object grantType = defaultCard.get("grantType");
		Object termType = defaultCard.get("termType");
		Object termDays = defaultCard.get("termDays");
		Object termStartAt = defaultCard.get("termStartAt");
		Object termEndAt = defaultCard.get("termEndAt");
		Object createTime = defaultCard.get("createTime");
		// 按规则升级的会员卡无限期，其他的需要判断期限
		if (grantType != null && CardGrantType.RULE.getCode() != Integer.valueOf(grantType.toString())) {
			if (termType != null) {
				int tt = Integer.valueOf(termType.toString());
				String nowDay = DateUtil.getDay();
				if (CardTermsType.DAYS.getCode() == tt) {
					// 1、有效期为 XX天
					int days = Integer.valueOf(termDays.toString());
					// 计算领取日期和当前日期相隔天数，是否大于设置的天数
					long daySub = DateUtil.getDaySub(createTime.toString(), nowDay);
					if (daySub > days) {
						obj.put("state", MemCardState.EXPIRED.getCode());
					}
				} else if (CardTermsType.RANGE.getCode() == tt) {
					// 2、有效期开始和结束时间，判断当前日期是否在生效和失效时间内
					if (!DateUtil.compareDate(nowDay, termStartAt.toString())) { 
						// 未到 生效 时间
						obj.put("state", MemCardState.UN_USED.getCode());
					} else if (!DateUtil.compareDate(termEndAt.toString(), nowDay)) { // 超过失效时间
						obj.put("state", MemCardState.EXPIRED.getCode());
					}
				}
			}
		}

		// 获得会员卡权益
		Long cardId = Long.valueOf("" + defaultCard.get("cardId"));
		List<CardRight> cardRights = cardRightDao.selectByCardId(cardId);
		JSONArray rights = new JSONArray();
		if (CollectionUtils.isNotEmpty(cardRights)) {
			for (CardRight cardRight : cardRights) {
				if (cardRight.getRightType().equals(RightType.DISCOUNT.getCode())) {
					JSONObject right = new JSONObject();
					right.put(RightType.DISCOUNT.getCode(), cardRight.getDiscount());
					rights.add(right);
				}
			}
		}
		
		// 超过限额则查询差额后使用的会员卡
		Object amountLimitCardId = defaultCard.get("amountLimitCardId");
		Object tradeAmountLimit = defaultCard.get("tradeAmountLimit");
		if (amountLimitCardId != null && tradeAmountLimit != null) {
			CardRight cardRight = cardRightDao.selectByRightType(Long.valueOf(amountLimitCardId.toString()),
					RightType.DISCOUNT.getCode());
			if (cardRight != null) {
				// 超额后使用的折扣
				Integer newDiscount = cardRight.getDiscount();
				// 当月总购买金额
				Integer tradeAmount = orderDao.selectMemTradeAmount(memberId);
				obj.put("newDiscount", newDiscount);
				obj.put("limitAmount", tradeAmountLimit);
				obj.put("availableAmount",
						Integer.valueOf(tradeAmountLimit.toString()) - (tradeAmount == null ? 0 : tradeAmount));
			}
		}
		
		obj.put("cardRights", rights);
		log.info("查找会员卡initCard>>obj=" + obj.toJSONString());
		return obj;
	}
	
}
