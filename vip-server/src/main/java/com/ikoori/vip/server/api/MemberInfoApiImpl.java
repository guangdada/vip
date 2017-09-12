package com.ikoori.vip.server.api;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.service.MemberInfoApi;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.constant.state.CouponUseState;
import com.ikoori.vip.common.constant.state.GrantType;
import com.ikoori.vip.common.constant.state.MemCardState;
import com.ikoori.vip.common.constant.state.MemSourceType;
import com.ikoori.vip.common.constant.state.RightType;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.WxUserMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.CardRight;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.CouponFetch;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.common.persistence.model.WxUser;
import com.ikoori.vip.common.util.RandomUtil;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.modular.biz.dao.CardDao;
import com.ikoori.vip.server.modular.biz.dao.CardRightDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;


/**  
* @ClassName: MemberInfoApiImpl  
* @Description: 会员信息（我的资料）
* @author :huanglin 
* @date 2017年9月11日  
*    
*/  
@Service
public class MemberInfoApiImpl implements MemberInfoApi {
	@Autowired
	GunsProperties gunsProperties;
	@Autowired
	MemberDao memberDao;
	@Autowired
	WxUserMapper wxUserMapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	CouponFetchMapper couponFetchMapper;
	@Autowired
	CardDao cardDao;
	@Autowired
	CardRightDao cardRightDao;
	@Autowired
	CouponMapper couponMapper;
	@Autowired
	MemberCardMapper memberCardMapper;

	/**   
	 * <p>Title: getMemberInfoByOpenId</p>   
	 * <p>Description:获取会员信息 </p>   
	 * @param openId
	 * @return   
	 * @see com.ikoori.vip.api.service.MemberInfoApi#getMemberInfoByOpenId(java.lang.String)   
	 */  
	@Override
	public JSONObject getMemberInfoByOpenId(String openId) {
		Member member = memberDao.getMemberByOpenId(openId);
		if(member == null){
			return null;
		}
		JSONObject obj = new JSONObject();
		obj.put("id", member.getId());
		obj.put("name", member.getName());
		obj.put("sex", member.getSex());
		obj.put("address", member.getAddress());
		//obj.put("birthday", DateUtil.getTime(member.getBirthday()));
		obj.put("birthday", member.getBirthday());
		obj.put("mobile", member.getMobile());
		obj.put("isActive",member.isIsActive());
		return obj;
	}

	
	/**   
	 * <p>Title: updateMemberInofByOpenId</p>   
	 * <p>Description: 修改会员信息 </p>   
	 * @param openId
	 * @param mobile 会员手机号
	 * @param name  会员姓名
	 * @param sex  会员性别
	 * @param birthday 会员生日
	 * @param address 会员地址
	 * @return   
	 * @see com.ikoori.vip.api.service.MemberInfoApi#updateMemberInofByOpenId(java.lang.String, java.lang.String, java.lang.String, int, java.util.Date, java.lang.String)   
	 */  
	@Transactional(readOnly = false)
	@Override
	public int updateMemberInofByOpenId(String openId, String mobile, String name, int sex, Date birthday,
			String address) {
		return memberDao.updateMemberInfoByOpenId(openId, name, mobile, sex, address, birthday);
	}

	/**   
	 * <p>Title: getMemberByMobile</p>   
	 * <p>Description:根据会员手机号获取会员</p>   
	 * @param mobile  会员手机号
	 * @return   
	 * @see com.ikoori.vip.api.service.MemberInfoApi#getMemberByMobile(java.lang.String)   
	 */  
	@Override
	public Object getMemberByMobile(String mobile) {
		// TODO Auto-generated method stub
		Member member=memberDao.getMemberByMobile(mobile);
		if(member==null){
			return null;
		}
		return member;
	}

	/**
	 * 会员访问公众号时，判断是否已经是 会员
	 * 如果不是会员，默认发送一张“关注微信”类别的会员卡
	 * 根据会员卡的权益，赠送优惠券和积分
	 */
	@Transactional(readOnly = false)
	@Override
	public int saveMemberInfo(UserInfo userInfo) throws Exception {
		Member member = memberDao.getMemberByOpenId(userInfo.getOpenid());
		if (member == null) {
			// 保存微信用户
			WxUser wxUser = new WxUser();
			setWxUserInfo(userInfo, wxUser);
			wxUserMapper.insert(wxUser);
			
			// 保存会员
			member = new Member();
			member.setOpenId(wxUser.getOpenid());
			member.setWxUserId(wxUser.getId());
			member.setIsActive(false);
			// 需要根据appid获得商户id
			member.setMerchantId(gunsProperties.getMerchantId());
			member.setPoints(0);
			member.setSourceType(MemSourceType.wechat.getCode());
			memberMapper.insert(member);

			// 查询获取类型为“关注微信”的会员卡
			Card card = cardDao.getCardByGrantTypeAndMerchantId(gunsProperties.getMerchantId(), GrantType.SUB_WX.getCode());
			if (card == null) {
				throw new BussinessException(500, "没有找到会员卡类型");
			}
			MemberCard memberCard = new MemberCard();
			memberCard.setMemberId(member.getId());
			memberCard.setCardId(card.getId());
			memberCard.setCardNumber(RandomUtil.generateCardNum(card.getCardNumberPrefix()));
			memberCard.setIsDefault(true);
			memberCard.setMerchantId(gunsProperties.getMerchantId());
			memberCard.setState(MemCardState.USED.getCode());
			memberCardMapper.insert(memberCard);

			// 获取会员卡权益
			List<CardRight> cardRights = cardRightDao.selectByCardId(card.getId());
			if (CollectionUtils.isNotEmpty(cardRights)) {
				for (CardRight cardRight : cardRights) {
					if (RightType.COUPON.getCode().equals(cardRight.getRightType())) {
						Coupon coupon = couponMapper.selectById(cardRight.getCouponId());
						Integer number = cardRight.getNumber();
						if (number != null) {
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
						member.setPoints(cardRight.getPoints());
						memberMapper.updateById(member);
					}
				}
			}
		} else {
			WxUser wxUser = wxUserMapper.selectById(member.getWxUserId());
			setWxUserInfo(userInfo, wxUser);
			wxUserMapper.updateById(wxUser);
		}
		return 1;
	}

	private void setWxUserInfo(UserInfo userInfo, WxUser wxUser) {
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
	}
}
