package com.ikoori.vip.server.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikoori.vip.api.service.ShareApi;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.ShareLogMapper;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.ShareLog;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;

/**
 * 邀请注册
 * 
 * @ClassName: ShareApiImpl
 * @author: chengxg
 * @date: 2017年10月16日 下午5:54:23
 */
@Service
public class ShareApiImpl implements ShareApi {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ShareLogMapper shareLogMapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	MemberDao memberDao;

	/**
	 * 保存邀请记录
	 * 
	 * @Title: saveShareLog
	 * @param shareOpenid
	 *            邀请人openId
	 * @param receiveOpenid
	 *            受邀人openId
	 * @param receiveIp
	 *            受邀人ip
	 * @throws Exception
	 * @date: 2017年10月9日 上午9:28:15
	 * @author: chengxg
	 */
	@Override
	public void saveShareLog(String shareOpenid, String receiveOpenid, String receiveIp) throws Exception {
		log.info("进入saveShareLog");
		// 邀请人不存在， 不处理
		Member shareMem = memberDao.getMemberByOpenId(shareOpenid);
		if (shareMem == null) {
			log.info("shareMem == null");
			return;
		}
		// 受邀人已经是会员不再处理
		Member receiveMem = memberDao.getMemberByOpenId(receiveOpenid);
		if (receiveMem != null) {
			log.info("receiveMem != null");
			return;
		}

		ShareLog shareLog = new ShareLog();
		shareLog.setShareOpenid(shareOpenid);
		shareLog.setReceiveOpenid(receiveOpenid);
		shareLog.setReceiveIp(receiveIp);
		shareLog.setReceiveStatus(false);
		shareLogMapper.insert(shareLog);
		log.info("结束saveShareLog");
	}

}
