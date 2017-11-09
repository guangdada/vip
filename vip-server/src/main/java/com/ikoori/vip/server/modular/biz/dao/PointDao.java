package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Point;

/**
 * 积分管理Dao
 *
 * @author fengshuonan
 * @Date 2017-07-31 09:59:02
 */
public interface PointDao {
	/**
	 * 分页查询
	 * @Title: getPointList   
	 * @param page
	 * @param name
	 * @param orderByField
	 * @param isAsc
	 * @param merchantId
	 * @return
	 * @date:   2017年9月21日 下午6:42:37 
	 * @author: chengxg
	 */
	List<Map<String, Object>> getPointList(@Param("page") Page<Point> page, @Param("name") String name,
			@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,
			@Param("merchantId") Long merchantId);

	/* 得到所以积分 */
	List<Map<String, Object>> selectPointListByMemberId(@Param("unionid") String unionid,@Param("start") Integer start);

	/**
	 * 获得所有的积分规则
	 * @Title: selectByMerchantId   
	 * @param merchantId
	 * @return
	 * @date:   2017年9月21日 下午6:43:02 
	 * @author: chengxg
	 */
	List<Point> selectByMerchantId(@Param("merchantId") Long merchantId);
	
	/**
	 * 获得关注微信类型的规则
	 * @Title: getSubscribeWx   
	 * @param merchantId
	 * @return
	 * @date:   2017年9月21日 下午6:44:02 
	 * @author: chengxg
	 */
	Point getSubscribeWx(@Param("merchantId") Long merchantId);
}
