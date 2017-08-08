package com.ikoori.vip.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Point;

/**
 * <p>
  * 积分规则 Mapper 接口
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
public interface PointMapper extends BaseMapper<Point> {
    List<Map<String, Object>> getPointList(@Param("page") Page<Point> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

}