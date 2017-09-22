package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.CouponUseState;
import com.ikoori.vip.common.constant.state.MemCardState;
import com.ikoori.vip.common.constant.state.PointTradeType;
import com.ikoori.vip.common.constant.state.RightType;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.CardMapper;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.PointTradeMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.CardRight;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.CouponFetch;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.PointTrade;
import com.ikoori.vip.common.util.RandomUtil;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.dao.CardRightDao;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.dao.MemberCardDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.service.IMemberService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;

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
	public Member selecByMobile(String mobile){
		Member member = new Member();
		member.setMobile(mobile);
		return memberMapper.selectOne(member);
	}

	@Override
	public Integer insert(Member member) {
		return memberMapper.insert(member);
	}


	@Override
	@Transactional(readOnly=false)
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
	@Transactional(readOnly=false)
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
	@Transactional(readOnly=false)
	public void updateMember(Member member,Long cardId,int point) {
		/*int points=member.getPoints();
		if(points+point>0){
			Member db = memberMapper.selectById(member.getId());
			// 乐观锁
			member.setVersion(db.getVersion());
			member.setPoints(points+point);
		}
		this.updateById(member);*/
		//更新交易积分
		if(point!=0){
			Member dbMember= selectById(member.getId());
			int count = memberDao.updatePoint(dbMember.getId(), point);
			if(count == 0){
				throw new BussinessException(500,"积分修改失败");
			}
			
			PointTrade pointTrade=new PointTrade();
			pointTrade.setMemberId(dbMember.getId());
			pointTrade.setMerchantId(dbMember.getMerchantId());
			/*pointTrade.setTradeType(3);
			boolean inOut=false;
			if(point>0){
				inOut=false;
			}else{
				inOut=true;
			}*/
			pointTrade.setTradeType(PointTradeType.GIVE.getCode());
			pointTrade.setInOut(point> 0 ? true: false);
			pointTrade.setPoint(point);
			pointTradeMapper.insert(pointTrade);
		}
		
		//更新会员卡
		/*MemberCard mc=new MemberCard();
	    mc.setMemberId(member.getId());
	    mc=memberCardMapper.selectOne(mc);
	    mc.setCardId(cardId);
		memberCardMapper.updateById(mc);*/
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
			String memNickName, String memMobile,Long cardId,String cardNumber,Integer isActive,String orderByField, boolean isAsc) {
		return memberDao.getMemberList(page, memName, memSex, memNickName, memMobile, cardId,cardNumber,isActive,orderByField, isAsc);
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
	 * 升级会员卡和权益
	 * @Title: upgradeMemCard   
	 * @param member
	 * @param card
	 * @date:   2017年9月18日 下午1:51:33 
	 * @author: chengxg
	 */
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
							int uu = couponDao.updateGetCountUser(coupon.getId(), member.getId()); // 跟新领取人数
							if (uu == 0) {
								log.info("修改领取人数失败");
								continue;
							}
							log.info("保存优惠券领取记录");
							for (int i = 0; i < number.intValue(); i++) {
								CouponFetch couponFetch = new CouponFetch();
								couponFetch.setMemberId(member.getId());
								couponFetch.setCouponId(cardRight.getCouponId());
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
							}
						}
					} else if (RightType.POINTS.getCode().equals(cardRight.getRightType())) {
						log.info("赠送积分:" + cardRight.getPoints());
						PointTrade pointTrade = new PointTrade();
						pointTrade.setInOut(true);
						pointTrade.setTradeType(PointTradeType.CARD.getCode());
						pointTrade.setPoint(cardRight.getPoints());
						pointTrade.setMemberId(member.getId());
						pointTrade.setMerchantId(member.getMerchantId());
						pointTrade.setTag("谢谢关注");
						pointTradeMapper.insert(pointTrade);
						//member.setPoints(cardRight.getPoints());
						memberDao.updatePoint(member.getId(),cardRight.getPoints());
						//memberMapper.updateById(member);
					}
				}
			}
			
			// 获得关注微信的积分规则
			
		}
		// 修改会员的默认会员卡
		log.info("修改会员的默认会员卡");
		updateDefaultCard(card.getId(),member.getId());
	}
	
	
	public void activeCoupon(String ve){
		
	}
}
