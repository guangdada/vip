package com.ikoori.vip.server.config.job;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ikoori.vip.common.constant.state.RedPackSendStatus;
import com.ikoori.vip.common.persistence.model.RedpackLog;
import com.ikoori.vip.common.util.DateUtil;
import com.ikoori.vip.server.modular.biz.service.IRedpackLogService;


/**
 * 红包发送结果查询
 * @ClassName:  RedpackQueryJob
 * @author: chengxg
 * @date:   2017年10月21日 下午1:35:08
 */
@Configuration
@EnableScheduling
public class RedpackQueryJob {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	IRedpackLogService redpackService;

	//@Scheduled(cron = "0 0 0/1 * * ?") // 每小时执行一次
	public void scheduler() {
		log.info(">>>>>>>>>>>>> 同步红包数据开始 ... " + DateUtil.getDayTime());
		List<RedpackLog> rs = redpackService.selectBySendStatus(RedPackSendStatus.SENDING.getCode());
		if (CollectionUtils.isNotEmpty(rs)) {
			for (RedpackLog redpackLog : rs) {
				try {
					redpackService.updateRedPackLog(redpackLog);
				} catch (Exception e) {
					log.error("同步红包数据异常：",e);
				}
			}
		}
		log.info(">>>>>>>>>>>>> 同步红包数据结束... " + DateUtil.getDayTime());
	}
}
