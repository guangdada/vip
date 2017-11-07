package com.ikoori.vip.server.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
	 * @param shareUnionid
	 *            邀请人unionid
	 * @param receiveUnionid
	 *            受邀人unionid
	 * @param receiveIp
	 *            受邀人ip
	 * @throws Exception
	 * @date: 2017年10月9日 上午9:28:15
	 * @author: chengxg
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void saveShareLog(String shareUnionid, String receiveUnionid, String receiveIp) throws Exception {
		log.info("进入saveShareLog>>shareUnionid=" + shareUnionid);
		log.info("进入saveShareLog>>receiveUnionid=" + receiveUnionid);
		log.info("进入saveShareLog>>receiveIp=" + receiveIp);
		// 邀请人不存在， 不处理
		Member shareMem = memberDao.getMemberByUnionid(shareUnionid);
		if (shareMem == null) {
			log.info("邀请人不存在");
			return;
		}
		// 受邀人不是会员 或者已激活不处理
		Member receiveMem = memberDao.getMemberByUnionid(receiveUnionid);
		if (receiveMem == null || receiveMem.isIsActive()) {
			log.info("受邀人不存在");
			return;
		}
		
		Wrapper<ShareLog> w = new EntityWrapper<ShareLog>();
		w.eq("share_unionid", shareUnionid);
		w.eq("receive_unionid", receiveUnionid);
		int count = shareLogMapper.selectCount(w);
		if(count > 0){
			log.info("已有邀请记录");
			return;
		}
		ShareLog shareLog = new ShareLog();
		shareLog.setShareUnionid(shareUnionid);
		shareLog.setReceiveUnionid(receiveUnionid);
		shareLog.setReceiveIp(receiveIp);
		shareLog.setReceiveStatus(false);
		shareLogMapper.insert(shareLog);
		log.info("结束saveShareLog");
	}

}
