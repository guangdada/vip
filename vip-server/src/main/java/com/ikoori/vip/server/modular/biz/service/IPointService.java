package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Point;

/**
 * 积分管理Service
 *
 * @author fengshuonan
 * @Date 2017-07-31 09:59:02
 */
public interface IPointService {
    List<Map<String, Object>> getPointList(@Param("page") Page<Point> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
    public void savePoint(Point point);
}
