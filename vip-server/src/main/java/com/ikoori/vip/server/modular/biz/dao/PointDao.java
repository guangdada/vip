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

	List<Map<String, Object>> getPointList(@Param("page") Page<Point> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,@Param("merchantId") Long merchantId);
}
