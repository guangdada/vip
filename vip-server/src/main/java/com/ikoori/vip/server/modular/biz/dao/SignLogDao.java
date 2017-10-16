package com.ikoori.vip.server.modular.biz.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 签到记录Dao
 *
 * @author chengxg
 * @Date 2017-09-28 21:50:31
 */
public interface SignLogDao {
	List<Map<String, Object>> getSignLogList(@Param("page") Page<Map<String, Object>> page, @Param("name") String name,
			@Param("nickname") String nickname, @Param("mobile") String mobile, @Param("signS") String signS,
			@Param("signE") String signE, @Param("merchantId") Long merchantId,
			@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
	
	/**
	 * 获得当月签到的日期
	 * @Title: getSignDates   
	 * @param merchantId
	 * @param memberId
	 * @return
	 * @date:   2017年10月6日 下午3:59:53 
	 * @author: chengxg
	 */
	List<String> getSignDates(@Param("merchantId") Long merchantId,@Param("memberId") Long memberId);
}
