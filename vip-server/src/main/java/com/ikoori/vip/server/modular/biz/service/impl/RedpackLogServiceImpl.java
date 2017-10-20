package com.ikoori.vip.server.modular.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.coolfish.weixin.redpack.protocol.RedPackResData;
import com.ikoori.vip.common.constant.state.RedPackSendStatus;
import com.ikoori.vip.common.persistence.dao.RedpackLogMapper;
import com.ikoori.vip.common.persistence.model.RedpackLog;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.core.util.RedPackUtil;
import com.ikoori.vip.server.modular.biz.dao.RedpackLogDao;
import com.ikoori.vip.server.modular.biz.service.IRedpackLogService;

/**
 * 红包记录Dao
 *
 * @author chengxg
 * @Date 2017-10-19 15:52:13
 */
@Service
public class RedpackLogServiceImpl implements IRedpackLogService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	RedpackLogDao redpackLogDao;
	@Autowired
	RedpackLogMapper redpackLogMapper;
	@Autowired
	GunsProperties gunsProperties;

	@Override
	public Integer deleteById(Long id) {
		return redpackLogMapper.deleteById(id);
	}

	@Override
	public Integer updateById(RedpackLog redpackLog) {
		return redpackLogMapper.updateById(redpackLog);
	}

	@Override
	public RedpackLog selectById(Long id) {
		return redpackLogMapper.selectById(id);
	}

	@Override
	public Integer insert(RedpackLog redpackLog) {
		return redpackLogMapper.insert(redpackLog);
	}

	@Override
	public List<Map<String, Object>> getRedpackLogList(Page<Map<String, Object>> page, Long merchantId, String billno,
			String openid, Integer sendStatus, String sendS, String sendE, Integer redpackId, String orderByField,
			boolean isAsc) {
		return redpackLogDao.getRedpackLogList(page, merchantId, billno, openid, sendStatus, sendS, sendE, redpackId,
				orderByField, isAsc);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveRedPackLog(int amount, String openid, String ip, Long merchantId, Long redpackId, String actName,
			String remark, String wishing) {
		log.info("进入saveRedPackLog>>amount = " + amount);
		log.info("进入saveRedPackLog>>openid = " + openid);
		log.info("进入saveRedPackLog>>ip = " + ip);
		log.info("进入saveRedPackLog>>merchantId = " + merchantId);
		log.info("进入saveRedPackLog>>redpackId = " + redpackId);
		log.info("进入saveRedPackLog>>actName = " + actName);
		log.info("进入saveRedPackLog>>remark = " + remark);
		log.info("进入saveRedPackLog>>wishing = " + wishing);
		log.info("进入saveRedPackLog>>certPath = " + gunsProperties.getCertPath());
		String billno = "kryhb" + new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date());
		RedPackResData rprd = RedPackUtil.redPackSend(amount, actName, remark, wishing, openid, billno,
				gunsProperties.getCertPath());
		// 通信成功则记录发送日志
		if ("SUCCESS".equals(rprd.getReturn_code())) {
			RedpackLog log = new RedpackLog();
			log.setBillno(billno);
			log.setIp(ip);
			log.setRedpackId(redpackId);
			log.setMerchantId(merchantId);
			log.setOpenid(openid);
			log.setReason(rprd.getErr_code_des());
			log.setSendAmount(amount);
			if ("SUCCESS".equals(rprd.getErr_code())) {
				log.setSendStatus(RedPackSendStatus.SENDING.getCode());
			} else {
				log.setSendStatus(RedPackSendStatus.FAILED.getCode());
			}
			redpackLogMapper.insert(log);
		}
	}
	
	public static void main(String[] args) {
		//RedpackLogServiceImpl d = new RedpackLogServiceImpl();
		//d.saveRedPackLog(1, "o19yZsw5CT7CDk_ikBRiGNbyu7Tw", "", merchantId, redpackId, actName, remark, wishing);
		String billno = "kryhb" + new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date());
		RedPackResData rprd = RedPackUtil.redPackSend(1, "活动名称", "活动备注", "恭喜发财，大吉大利", "o19yZsw5CT7CDk_ikBRiGNbyu7Tw", billno,
				"D:\\cert\\apiclient_cert.p12");
		System.out.println(rprd);
	}

}
