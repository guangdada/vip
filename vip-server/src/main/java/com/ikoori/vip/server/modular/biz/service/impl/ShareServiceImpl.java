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
import com.ikoori.vip.common.constant.state.PointTradeType;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.dao.ShareLogMapper;
import com.ikoori.vip.common.persistence.dao.ShareMapper;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.Share;
import com.ikoori.vip.common.persistence.model.ShareLog;
import com.ikoori.vip.common.util.DateUtil;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.ShareDao;
import com.ikoori.vip.server.modular.biz.dao.ShareLogDao;
import com.ikoori.vip.server.modular.biz.service.ICouponFetchService;
import com.ikoori.vip.server.modular.biz.service.IPointTradeService;
import com.ikoori.vip.server.modular.biz.service.IShareService;

/**
 * 分享规则Dao
 *
 * @author chengxg
 * @Date 2017-10-16 14:22:06
 */
@Service
public class ShareServiceImpl implements IShareService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ShareDao shareDao;
	@Autowired
	ShareMapper shareMapper;
	@Autowired
	MemberDao memberDao;
	@Autowired
	ShareLogMapper shareLogMapper;
	@Autowired
	ShareLogDao shareLogDao;
	@Autowired
	IPointTradeService pointTradeService;
	@Autowired
	ICouponFetchService couponFetchService;
	@Autowired
	CouponMapper couponMapper;

	@Override
	public Integer deleteById(Long id) {
		return shareMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Share share) {
		return shareMapper.updateById(share);
	}

	@Override
	public Share selectById(Long id) {
		return shareMapper.selectById(id);
	}

	@Override
	public Integer insert(Share share) {
		return shareMapper.insert(share);
	}

	@Override
	public List<Map<String, Object>> getShareList(Page<Map<String, Object>> page, Long merchantId, String orderByField,
			boolean isAsc) {
		return shareDao.getShareList(page, merchantId, orderByField, isAsc);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveShare(Share share) {
		if (share.getId() == null) {
			if (!checkShare(share.getMerchantId())) {
				throw new BussinessException(500, "只能有一条分享规则");
			}
			shareMapper.insert(share);
		} else {
			Share sharedb = shareMapper.selectById(share.getId());
			sharedb.setCouponId(share.getCouponId());
			sharedb.setPoint(share.getPoint());
			sharedb.setShareCount(share.getShareCount());
			sharedb.setUpdateTime(new Date());
			shareMapper.updateAllColumnById(sharedb);
		}
	}

	/**
	 * 只能有一个邀请规则
	 * 
	 * @Title: checkShare
	 * @param merchantId
	 * @return
	 * @date: 2017年10月9日 上午9:23:50
	 * @author: chengxg
	 */
	public boolean checkShare(Long merchantId) {
		Wrapper<Share> w = new EntityWrapper<Share>();
		w.eq("merchant_id", merchantId);
		w.eq("status", 1);
		int count = shareMapper.selectCount(w);
		return count > 0 ? false : true;
	}

	/**
	 * 获得当前激活用户的邀请人
	 * 
	 * @Title: getShareLog
	 * @param receiveOpenId
	 * @return
	 * @date: 2017年10月9日 上午11:19:16
	 * @author: chengxg
	 */
	public ShareLog getShareLog(String receiveOpenId) {
		Wrapper<ShareLog> w = new EntityWrapper<ShareLog>();
		w.eq("receive_openid", receiveOpenId);
		w.eq("receive_status", 0);
		w.eq("status", 1);
		w.orderBy("id", false);
		w.last(" limit 1");
		List<ShareLog> shareLogs = shareLogMapper.selectList(w);
		return CollectionUtils.isEmpty(shareLogs) ? null : shareLogs.get(0);
	}

	/**
	 * 获得分享规则
	 * 
	 * @Title: getShare
	 * @param merchantId
	 * @return
	 * @date: 2017年10月9日 上午11:06:05
	 * @author: chengxg
	 */
	public Share getShare(Long merchantId) {
		Wrapper<Share> w = new EntityWrapper<Share>();
		w.eq("merchant_id", merchantId);
		w.eq("status", 1);
		List<Share> shares = shareMapper.selectList(w);
		return CollectionUtils.isEmpty(shares) ? null : shares.get(0);
	}

	/**
	 * 获得邀请人某月已经邀请成功的人数
	 * 
	 * @Title: getShareCount
	 * @param shareOpenid
	 * @return
	 * @date: 2017年10月9日 上午11:24:09
	 * @author: chengxg
	 */
	public int getShareCount(String shareOpenid, Date date) {
		Wrapper<ShareLog> w = new EntityWrapper<ShareLog>();
		w.eq("share_openid", shareOpenid);
		w.eq("receive_status", 1);
		w.eq("status", 1);
		return shareLogMapper.selectCount(w);
	}

	/**
	 * 获得邀请人，邀请某个IP的数量
	 * 
	 * @Title: getShareCountByIp
	 * @param shareOpenid
	 * @param ip
	 * @return
	 * @date: 2017年10月9日 上午11:25:13
	 * @author: chengxg
	 */
	public int getShareCountByIp(String shareOpenid, String ip) {
		Wrapper<ShareLog> w = new EntityWrapper<ShareLog>();
		w.eq("share_openid", shareOpenid);
		w.eq("receive_status", 1);
		w.eq("receive_ip", ip);
		w.eq("status", 1);
		return shareLogMapper.selectCount(w);
	}

	/**
	 * 受邀人激活成功后，奖励邀请人
	 * 
	 * @Title: activeShare
	 * @param receiveMem 受邀人
	 * @date: 2017年10月9日 上午10:04:53
	 * @author: chengxg
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void activeShare(Member receiveMem) {
		log.info("进入activeShare" + receiveMem.getOpenId());
		//Member receiveMem = memberDao.getMemberByOpenId(receiveOpenid);
		// 获得分享规则
		Share share = getShare(receiveMem.getMerchantId());
		if (share == null) {
			log.info("没有设置分享规则");
			return;
		}
		// 获得邀请记录，得到邀请人信息
		ShareLog shareLog = getShareLog(receiveMem.getOpenId());
		if (shareLog == null) {
			log.info("没有找到邀请记录");
			return;
		}

		// 判断邀请人当月已经邀请人数(激活成功为准)是否已经超过限制
		if (share.getShareCount() != null) {
			int shareCount = shareLogDao.getShareCount(shareLog.getShareOpenid(),
					DateUtil.getDay(shareLog.getCreateTime()));
			if (shareCount >= share.getShareCount()) {
				log.info("邀请人当月邀请人数为：" + shareCount + "超过限制的：" + share.getShareCount());
				return;
			}
		}

		// 同一IP仅限第一个注册的好友加积分
		int shareIpCount = getShareCountByIp(shareLog.getShareOpenid(), shareLog.getReceiveIp());
		if (shareIpCount >= 1) {
			log.info("邀请人已经邀请过ip:" + shareLog.getReceiveIp() + "获得奖励");
			return;
		}

		// 邀请人
		Member shareMem = memberDao.getMemberByOpenId(shareLog.getShareOpenid());
		if (shareMem == null) {
			log.info("没有找到openId:" + shareLog.getShareOpenid() + "的邀请人");
			return;
		}

		// 赠送邀请人积分或优惠券
		Integer point = share.getPoint();
		if (point != null) {
			log.info("奖励邀请人积分：" + point);
			pointTradeService.savePointTrade(true, PointTradeType.SHARE.getCode(), point, shareMem.getId(), null,
					shareMem.getMerchantId(), null, "");
		}
		Long couponId = share.getCouponId();
		if (couponId != null) {
			Coupon coupon = couponMapper.selectById(couponId);
			log.info("奖励邀请人优惠券：" + coupon.getName());
			couponFetchService.saveCouponFetch(shareMem, coupon,null);
		}

		// 修改邀请记录状态为“邀请成功”
		shareLog.setReceiveStatus(true);
		shareLogMapper.updateById(shareLog);
		log.info("结束activeShare");
	}
}
