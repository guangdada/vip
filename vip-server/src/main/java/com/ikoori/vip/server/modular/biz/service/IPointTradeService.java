package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.PointTrade;

/**
 * 积分明细Service
 *
 * @author chengxg
 * @Date 2017-09-20 16:36:35
 */
public interface IPointTradeService {
	public Integer deleteById(Long id);

	public Integer updateById(PointTrade pointTrade);

	public PointTrade selectById(Long id);

	public Integer insert(PointTrade pointTrade);

	/**
	 * 分页查询积分明细
	 * @Title: getPointTradeList   
	 * @param page
	 * @param nickname
	 * @param mobile
	 * @param inOut
	 * @param pointId
	 * @param merchantId
	 * @param orderByField
	 * @param isAsc
	 * @return
	 * @date:   2017年9月20日 下午8:50:58 
	 * @author: chengxg
	 */
	List<Map<String, Object>> getPointTradeList(Page<Map<String, Object>> page, String nickname, String mobile,
			Integer inOut, Long pointId,Long merchantId, String orderByField, boolean isAsc);

}
