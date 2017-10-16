package com.ikoori.vip.server.api;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.service.MemberInfoApi;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.constant.state.CardGrantType;
import com.ikoori.vip.common.constant.state.MemSourceType;
import com.ikoori.vip.common.constant.state.PointTradeType;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.PointTradeMapper;
import com.ikoori.vip.common.persistence.dao.WxUserMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.Point;
import com.ikoori.vip.common.persistence.model.WxUser;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.modular.biz.dao.CardDao;
import com.ikoori.vip.server.modular.biz.dao.CardRightDao;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.PointDao;
import com.ikoori.vip.server.modular.biz.service.IMemberService;
import com.ikoori.vip.server.modular.biz.service.IPointTradeService;

/**
 * @ClassName: MemberInfoApiImpl
 * @Description: 会员信息（我的资料）
 * @author :huanglin
 * @date 2017年9月11日
 * 
 */
@Service
public class MemberInfoApiImpl implements MemberInfoApi {
	private Logger log = LoggerFactory.getLogger(this.getClass());
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
	@Autowired
	CouponDao couponDao;
	@Autowired
	PointTradeMapper pointTradeMapper;
	@Autowired
	IMemberService memberService;
	@Autowired
	PointDao pointDao;
	@Autowired
	IPointTradeService pointTradeService;

	/**
	 * <p>
	 * Title: getMemberInfoByOpenId
	 * </p>
	 * <p>
	 * Description:获取会员信息
	 * </p>
	 * 
	 * @param openId
	 * @return
	 * @see com.ikoori.vip.api.service.MemberInfoApi#getMemberInfoByOpenId(java.lang.String)
	 */
	@Override
	public JSONObject getMemberInfoByOpenId(String openId) {
		Member member = memberDao.getMemberByOpenId(openId);
		if (member == null) {
			return null;
		}
		JSONObject obj = new JSONObject();
		obj.put("id", member.getId());
		obj.put("name", member.getName());
		obj.put("sex", member.getSex());
		obj.put("address", member.getAddress());
		// obj.put("birthday", DateUtil.getTime(member.getBirthday()));
		obj.put("birthday", member.getBirthday());
		obj.put("mobile", member.getMobile());
		obj.put("isActive", member.isIsActive());
		obj.put("area", member.getArea());
		return obj;
	}

	/**
	 * <p>
	 * Title: updateMemberInofByOpenId
	 * </p>
	 * <p>
	 * Description: 修改会员信息
	 * </p>
	 * 
	 * @param openId
	 * @param mobile
	 *            会员手机号
	 * @param name
	 *            会员姓名
	 * @param sex
	 *            会员性别
	 * @param birthday
	 *            会员生日
	 * @param address
	 *            会员地址
	 * @return
	 * @see com.ikoori.vip.api.service.MemberInfoApi#updateMemberInofByOpenId(java.lang.String,
	 *      java.lang.String, java.lang.String, int, java.util.Date,
	 *      java.lang.String)
	 */
	@Transactional(readOnly = false)
	@Override
	public int updateMemberInfoByOpenId(String openId, String mobile, String name, int sex, Date birthday,
			String address,String area) {
		return memberDao.updateMemberInfoByOpenId(openId, name, mobile, sex, address, birthday,area);
	}

	/**
	 * <p>
	 * Title: getMemberByMobile
	 * </p>
	 * <p>
	 * Description:根据会员手机号获取会员
	 * </p>
	 * 
	 * @param mobile
	 *            会员手机号
	 * @return
	 * @see com.ikoori.vip.api.service.MemberInfoApi#getMemberByMobile(java.lang.String)
	 */
	@Override
	public Object getMemberByMobile(String mobile) {
		// TODO Auto-generated method stub
		Member member = memberDao.getMemberByMobile(mobile);
		if (member == null) {
			return null;
		}
		return member;
	}

	/**
	 * 会员访问公众号时，判断是否已经是 会员 如果不是会员，默认发送一张“关注微信”类别的会员卡 根据会员卡的权益，赠送优惠券和积分
	 * @Title: saveMemberInfo   
	 * @param userInfo
	 * @return
	 * @throws Exception
	 * @date:   2017年9月18日 上午12:26:24 
	 * @author: chengxg
	 */
	@Transactional(readOnly = false)
	@Override
	public void saveMemberInfo(UserInfo userInfo) throws Exception {
		log.info("关注微信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		JSONObject obj = new JSONObject();
		obj.put("code", false);
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
				obj.put("msg", "没有找到会员卡类型");
				throw new Exception(obj.toJSONString());
			}

			log.info("保存会员卡领取记录");
			memberService.upgradeMemberCard(member, card);
			
			// 关注微信返回积分
			Point point = pointDao.getSubscribeWx(member.getMerchantId());
			if (point != null) {
				pointTradeService.savePointTrade(true, PointTradeType.SUBSCRIBE_WX.getCode(), point.getPoints(),
						member.getId(), point.getId(), member.getMerchantId(),null, "");
			}
		} else {
			log.info("用户已经存在，开始更新微信账号信息");
			WxUser wxUser = new WxUser();
			wxUser.setOpenid(userInfo.getOpenid());
			wxUser = wxUserMapper.selectOne(wxUser);
			setWxUserInfo(userInfo, wxUser);
			wxUserMapper.updateById(wxUser);
		}
		log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<关注微信");
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
