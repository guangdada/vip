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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.constant.state.CardGrantType;
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
import com.ikoori.vip.common.util.RandomUtil;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.dao.CardDao;
import com.ikoori.vip.server.modular.biz.dao.CardRightDao;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.dao.MemberCardDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
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
		upgradeMemberCard(member, card);
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
			String memNickName, String memMobile,Long cardId,String cardNumber,Integer isActive,String openId,String orderByField, boolean isAsc) {
		return memberDao.getMemberList(page, memName, memSex, memNickName, memMobile, cardId,cardNumber,isActive,openId,orderByField, isAsc);
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
		wxUser.setCountry(userInfo.getCity());
		wxUser.setHeadimgurl(userInfo.getHeadimgurl());
		wxUser.setIsSubscribe(true);
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
	public void saveMember(UserInfo userInfo) throws Exception{
		log.info("进去saveMember>>userInfo:" + userInfo.toString());
		if(userInfo.getOpenid() == null){
			log.info("微信用户openid为空");
			throw new Exception("微信用户openid为空");
		}
		synchronized (userInfo.getOpenid().intern()) {
			Member member = memberDao.getMemberByOpenId(userInfo.getOpenid());
			if (member == null) {
				// 保存微信用户
				log.info("保存微信用户");
				WxUser wxUser = new WxUser();
				setWxUserInfo(userInfo, wxUser);
				wxUserMapper.insert(wxUser);

				// 保存会员
				log.info("保存会员信息");
				member = new Member();
				member.setOpenId(wxUser.getOpenid());
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
					//obj.put("msg", "没有找到会员卡类型");
					//throw new Exception(obj.toJSONString());
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
				log.info("用户已经存在，开始更新微信账号信息");
				WxUser wxUser = new WxUser();
				wxUser.setOpenid(userInfo.getOpenid());
				wxUser = wxUserMapper.selectOne(wxUser);
				setWxUserInfo(userInfo, wxUser);
				wxUserMapper.updateById(wxUser);
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
	 * 根据openid查询会员
	 * @Title: selectByOpenid   
	 * @param openid
	 * @return
	 * @date:   2017年10月19日 上午10:19:25 
	 * @author: chengxg
	 */
	@Override
	public Member selectByOpenid(String openid) {
		Member member = memberDao.getMemberByOpenId(openid);
		return member;
	}

	@Override
	public Map<String, Object> getWxUserByOpenId(String openId) {
		Map<String, Object> member = memberDao.getWxUserByOpenId(openId);
		return member;
	}
}
