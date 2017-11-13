package com.ikoori.vip.server.api;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.service.MemberInfoApi;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.constant.state.RedpackSendType;
import com.ikoori.vip.common.constant.state.RedpackType;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.PointTradeMapper;
import com.ikoori.vip.common.persistence.dao.WxUserMapper;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.Redpack;
import com.ikoori.vip.common.persistence.model.WxUser;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.modular.biz.dao.CardDao;
import com.ikoori.vip.server.modular.biz.dao.CardRightDao;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.PointDao;
import com.ikoori.vip.server.modular.biz.service.IMemberService;
import com.ikoori.vip.server.modular.biz.service.IPointTradeService;
import com.ikoori.vip.server.modular.biz.service.IRedpackLogService;
import com.ikoori.vip.server.modular.biz.service.IRedpackService;
import com.ikoori.vip.server.modular.biz.service.IShareService;

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
	@Autowired
	IShareService shareService;
	@Autowired
	IRedpackLogService redpackLogService;
	@Autowired
	IRedpackService redpackService;

	/**
	 * <p>
	 * Title: getMemberInfoByUnionid
	 * </p>
	 * <p>
	 * Description:获取会员信息
	 * </p>
	 * 
	 * @param unionid
	 * @return
	 * @see com.ikoori.vip.api.service.MemberInfoApi#getMemberInfoByUnionid(java.lang.String)
	 */
	@Override
	public JSONObject getMemberInfoByUnionid(String unionid) {
		log.info("进入getMemberInfoByUnionid");
		log.info("进入getMemberInfoByUnionid>>unionid=" + unionid);
		Member member = memberDao.getMemberByUnionid(unionid);
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
		log.info("结束getMemberInfoByUnionid");
		return obj;
	}

	/**
	 * <p>
	 * Title: updateMemberInofByUnionid
	 * </p>
	 * <p>
	 * Description: 修改会员信息
	 * </p>
	 * 
	 * @param unionid
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
	 * @see com.ikoori.vip.api.service.MemberInfoApi#updateMemberInofByUnionid(java.lang.String,
	 *      java.lang.String, java.lang.String, int, java.util.Date,
	 *      java.lang.String)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public int updateMemberInfoByUnionid(String unionid, String mobile, String name, int sex, Date birthday,
			String address, String area) {
		log.info("进入updateMemberInfoByUnionid");
		log.info("进入updateMemberInfoByUnionid>>unionid=" + unionid);
		return memberDao.updateMemberInfoByUnionid(unionid, name, mobile, sex, address, birthday, area);
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
		log.info("进入getMemberByMobile");
		log.info("进入getMemberByMobile>>mobile=" + mobile);
		Member member = memberDao.getMemberByMobile(mobile);
		if (member == null) {
			log.info("member == null");
			return null;
		}
		log.info("结束getMemberByMobile");
		return member;
	}

	/**
	 * 会员访问公众号时，判断是否已经是 会员 如果不是会员，默认发送一张“关注微信”类别的会员卡 根据会员卡的权益，赠送优惠券和积分
	 * 
	 * @Title: saveMemberInfo
	 * @param userInfo
	 * @param update 是否更新微信信息（小程序不要更新）
	 * @return
	 * @throws Exception
	 * @date: 2017年9月18日 上午12:26:24
	 * @author: chengxg
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void saveMemberInfo(UserInfo userInfo,boolean update) throws Exception {
		log.info("进入saveMemberInfo>> userInfo ==" + userInfo.toString());
		log.info("关注微信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		memberService.saveMember(userInfo,update);
		log.info("结束saveMemberInfo");
		log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<关注微信");
	}

	/**
	 * 激活会员
	 * 
	 * @Title: activeMemberByUnionid
	 * @param unionid
	 * @param mobile
	 * @param ip 
	 * @param sendPack 发送红包
	 * @return
	 * @date: 2017年10月9日 上午10:54:16
	 * @author: chengxg
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int activeMemberByUnionid(String unionid, String mobile, String ip,boolean sendPack) {
		synchronized (mobile.intern()) {
			log.info("进入activeMemberByUnionid>>unionid=" + unionid);
			log.info("进入activeMemberByUnionid>>mobile=" + mobile);
			int count = memberDao.registerMember(unionid, mobile);
			if (count > 0) {
				Member receiveMem = memberDao.getMemberByUnionid(unionid);
				shareService.activeShare(receiveMem);
				if(sendPack){
					Redpack redpack = redpackService.selectByPackType(RedpackType.re.getCode(), receiveMem.getMerchantId());
					if (redpack != null) {
						Integer amount = redpack.getAmount().multiply(new BigDecimal(100)).intValue();
						if (redpack.getSendType().intValue() == RedpackSendType.random.getCode()) {
							int max = redpack.getMaxAmount().multiply(new BigDecimal(100)).intValue();
							int min = redpack.getMinAmount().multiply(new BigDecimal(100)).intValue();
							amount = (int) (Math.random() * (max - min + 1) + min);
						}

						WxUser wxuser = new WxUser();
						wxuser.setUnionid(unionid);
						wxuser = wxUserMapper.selectOne(wxuser);
						if (wxuser != null) {
							redpackLogService.saveRedPackLog(amount, unionid, wxuser.getOpenid(), ip,
									receiveMem.getMerchantId(), redpack.getId(), redpack.getActName(), redpack.getRemark(),
									redpack.getWishing());
						}
					}
				}
			}
			log.info("结束activeMemberByUnionid");
			return count;
		}
	}

	/**
	 * @Title: getWxUserByUnionid @Description: 获取微信号的信息 @date: 2017年10月10日
	 *         下午3:42:02 @author: huanglin @throws
	 */
	@Override
	public Object getWxUserByUnionid(String unionid) {
		log.info("进入getWxUserByUnionid>>unionid=" + unionid);
		return memberDao.getWxUserByUnionid(unionid);
	}

}
