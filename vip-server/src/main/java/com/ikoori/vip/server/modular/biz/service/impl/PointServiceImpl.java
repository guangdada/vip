package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.PointMapper;
import com.ikoori.vip.common.persistence.model.Point;
import com.ikoori.vip.server.modular.biz.service.IPointService;

/**
 * 积分管理Dao
 *
 * @author fengshuonan
 * @Date 2017-07-31 09:59:02
 */
@Service
public class PointServiceImpl implements IPointService {
	@Autowired
	PointMapper pointMapper;

	@Override
	public List<Map<String, Object>> getPointList(Page<Point> page, String name, String orderByField,
			boolean isAsc) {
		return pointMapper.getPointList(page, name, orderByField, isAsc);
	}
	
	public void savePoint(Point point){
		pointMapper.insert(point);
	}
}
