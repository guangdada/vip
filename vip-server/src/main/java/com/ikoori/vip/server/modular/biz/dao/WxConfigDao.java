package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.WxConfig;

/**
 * 公众号管理Dao
 *
 * @author chengxg
 * @Date 2017-07-31 09:58:11
 */
public interface WxConfigDao {
	/**
	 * 查询商户公众号
	 * @param page
	 * @param appid
	 * @param orderByField
	 * @param isAsc
	 * @return
	 */
	List<Map<String, Object>> getWxConfigList(@Param("page") Page<WxConfig> page, @Param("appid") String appid,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

}
