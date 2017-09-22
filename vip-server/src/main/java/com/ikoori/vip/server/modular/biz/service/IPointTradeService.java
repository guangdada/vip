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
	 * @param tradeType
	 * @param orderByField
	 * @param isAsc
	 * @return
	 * @date:   2017年9月22日 上午11:45:38 
	 * @author: chengxg
	 */
	List<Map<String, Object>> getPointTradeList(Page<Map<String, Object>> page, String nickname, String mobile,
			Integer inOut, Long pointId,Long merchantId,Integer tradeType, String orderByField, boolean isAsc);
	
	/**
	 * 保存积分交易记录
	 * @Title: savePointTrade   
	 * @param inout
	 * @param tradeType
	 * @param points
	 * @param memberId
	 * @param pointId
	 * @param merchantId
	 * @param storeId
	 * @param tag
	 * @return
	 * @date:   2017年9月22日 下午2:22:41 
	 * @author: chengxg
	 */
	public boolean savePointTrade(Boolean inout, Integer tradeType, Integer points, Long memberId, Long pointId,
			Long merchantId,Long storeId, String tag);

}
