package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.RedpackLog;

/**
 * 红包记录Service
 *
 * @author chengxg
 * @Date 2017-10-19 15:52:13
 */
public interface IRedpackLogService {
	public Integer deleteById(Long id);

	public Integer updateById(RedpackLog redpackLog);

	public RedpackLog selectById(Long id);

	public Integer insert(RedpackLog redpackLog);

	List<Map<String, Object>> getRedpackLogList(Page<Map<String, Object>> page, Long merchantId, String billno,
			String openid, Integer sendStatus, String sendS, String sendE, Integer redpackId, String orderByField,
			boolean isAsc);

	/**
	 * 发送微信红包
	 * @Title: saveRedPackLog   
	 * @param amount
	 * @param openid
	 * @param ip
	 * @param merchantId
	 * @param redpackId
	 * @param actName
	 * @param remark
	 * @param wishing
	 * @date:   2017年10月19日 下午6:13:57 
	 * @author: chengxg
	 */
	public void saveRedPackLog(int amount, String openid, String ip, Long merchantId, Long redpackId, String actName,
			String remark, String wishing);
	
	/**
	 * 同步红包发送结果
	 * @Title: updateRedPackLog   
	 * @param redpackLog
	 * @date:   2017年10月21日 下午12:43:09 
	 * @author: chengxg
	 */
	public void updateRedPackLog(RedpackLog redpackLog);
	
	/**
	 * 根据发送状态查询
	 * @Title: selectBySendStatus   
	 * @param sendStatus
	 * @date:   2017年10月21日 下午1:42:21 
	 * @author: chengxg
	 */
	public List<RedpackLog> selectBySendStatus(Integer sendStatus);
}
