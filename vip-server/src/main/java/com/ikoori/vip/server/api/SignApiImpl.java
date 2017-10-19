package com.ikoori.vip.server.api;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ikoori.vip.api.service.SignApi;
import com.ikoori.vip.common.constant.state.PointTradeType;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.SignLogMapper;
import com.ikoori.vip.common.persistence.dao.SignMapper;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.Sign;
import com.ikoori.vip.common.persistence.model.SignLog;
import com.ikoori.vip.common.util.DateUtil;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.SignLogDao;
import com.ikoori.vip.server.modular.biz.service.IPointTradeService;

/**
 * 签到
 * 
 * @ClassName: SignApiImpl
 * @author: chengxg
 * @date: 2017年9月29日 上午10:58:57
 */
@Service
public class SignApiImpl implements SignApi {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	MemberDao memberDao;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	IPointTradeService pointTradeService;
	@Autowired
	SignLogMapper signLogMapper;
	@Autowired
	SignMapper signMapper;
	@Autowired
	SignLogDao signLogDao;

	/**
	 * 获得签到规则
	 * 
	 * @Title: getSignList
	 * @param merchantId
	 * @return
	 * @date: 2017年9月29日 下午4:00:08
	 * @author: chengxg
	 */
	public List<Sign> getSignList(Long merchantId) {
		Wrapper<Sign> w = new EntityWrapper<Sign>();
		w.eq("status", 1).eq("merchant_id", merchantId).orderBy("times", false);
		return signMapper.selectList(w);
	}

	/**
	 * 获得签到信息
	 * 
	 * @Title: getSignInfo
	 * @param openId
	 * @return
	 * @throws Exception
	 * @date: 2017年9月29日 下午4:21:09
	 * @author: chengxg
	 */
	public JSONObject getSignInfo(String openId) throws Exception {
		log.info("进入getSignInfo>>openId=" + openId);
		JSONObject obj = new JSONObject();
		// openId获得会员
		Member member = memberDao.getMemberByOpenId(openId);
		obj.put("points", member == null ? 0 : member.getPoints());
		obj.put("signDate", member == null ? 0 : member.getSignDate());
		obj.put("signDays", member == null ? 0 : member.getSignDays());
		obj.put("signTotalDays", member == null ? 0 : member.getSignTotalDays());
		List<String> dates = signLogDao.getSignDates(member.getMerchantId(), member.getId());
		obj.put("signDates", dates);
		return obj;
	}

	/**
	 * 每日签到方法
	 * 
	 * @Title: signIn
	 * @param openId
	 * @return
	 * @throws Exception
	 * @date: 2017年9月29日 下午4:07:58
	 * @author: chengxg
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public JSONObject signIn(String openId) throws Exception {
		log.info("进入signIn>>openId=" + openId);
		JSONObject obj = new JSONObject();
		obj.put("code", true);
		obj.put("msg", "签到成功");
		// openId获得会员
		Member member = memberDao.getMemberByOpenId(openId);
		if (member == null) {
			obj.put("msg", "您还不是会员哦");
			throw new Exception(obj.toJSONString());
		}

		// 当前时间
		Date nowDate = new Date();
		Date yesDate = DateUtil.getAfterDayDate(-1);
		String nowDateStr = DateUtil.getDay(new Date());
		String yesDateStr = DateUtil.getDay(yesDate);

		synchronized (openId.intern()) {
			// 最后签到日期
			Date signDate = member.getSignDate();
			// 获得会员最后签到日期
			// 如果最后签到日期为null，表示历史第一次签到，修改连续签到天数=1,修改最后签到日期为今天
			if (signDate == null) {
				member.setSignDays(1);
			}
			String signDateStr = signDate == null ? "" : DateUtil.getDay(signDate);
			// 如果最后签到日期是今天，提示今天已经签到过， 不做后续处理
			if (nowDateStr.equals(signDateStr)) {
				obj.put("msg", "今天已经签到过了");
				throw new Exception(obj.toJSONString());
			} else {
				// 如果最后签到日期不是今天，保存今天的签到记录
				SignLog signLog = new SignLog();
				signLog.setMemberId(member.getId());
				signLog.setMerchantId(member.getMerchantId());
				signLogMapper.insert(signLog);

				// 如果最后签到日期是昨天，修改连续签到天数+1
				if (signDateStr.equals(yesDateStr)) {
					member.setSignDays(member.getSignDays() + 1);
				} else {// 如果最后签到日期不是昨天，修改连续签到天数=1
					member.setSignDays(1);
				}

				// 修改签到信息
				member.setSignDate(nowDate);
				member.setSignTotalDays(member.getSignTotalDays() + 1);
				memberMapper.updateById(member);

				// 连续签到天数
				Integer signDays = member.getSignDays();
				// 获得签到规则
				boolean getPoint = false;
				List<Sign> signs = getSignList(member.getMerchantId());
				for (Sign sign : signs) {
					if (!sign.isOnce()) {
						Integer times = sign.getTimes();
						Integer score = sign.getScore();
						// times 等于1表示不需要连续， 每天签都能得积分
						if (signDays % times == 0) {
							boolean result = pointTradeService.savePointTrade(true, PointTradeType.MARK.getCode(),
									score, member.getId(), null, member.getMerchantId(), null, "签到");
							if (!result) {
								obj.put("msg", "签到失败！");
								throw new Exception(obj.toJSONString());
							}
							// 连续签到为1天的情况， 可以同其他规则一起生效，不管是不是设置了“仅领一次”
							if(times !=1){
								getPoint = true;// 领取过后 ， “仅领一次”的规则就不处理了
							}
						}
					}
				}

				// 处理“仅领一次”的规则
				if (!getPoint) {
					for (Sign sign : signs) {
						if (sign.isOnce()) {
							Integer times = sign.getTimes();
							Integer score = sign.getScore();
							if (signDays % times == 0) {
								boolean result = pointTradeService.savePointTrade(true, PointTradeType.MARK.getCode(),
										score, member.getId(), null, member.getMerchantId(), null, "签到");
								if (!result) {
									obj.put("msg", "签到失败！");
									throw new Exception(obj.toJSONString());
								}
								break; // 获取一次便退出
							}
						}
					}
				}
			}
		}
		log.info("结束signIn" + openId);
		return obj;
	}
}
