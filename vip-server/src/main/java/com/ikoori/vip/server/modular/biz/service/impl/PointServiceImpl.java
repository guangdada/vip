package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.PointMapper;
import com.ikoori.vip.common.persistence.model.Point;
import com.ikoori.vip.server.modular.biz.dao.PointDao;
import com.ikoori.vip.server.modular.biz.service.IPointService;
import com.ikoori.vip.server.modular.biz.warpper.PointWarpper;

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
	
	@Autowired
	PointDao pointDao;

	@Override
	public List<Map<String, Object>> getPointList(Page<Point> page, String name, String orderByField,
			boolean isAsc,Long merchantId) {
		return pointDao.getPointList(page, name, orderByField, isAsc,merchantId);
	}
	
	/**
	 * 保存积分规则
	 * @Title: savePoint   
	 * @param point
	 * @date:   2017年9月20日 下午9:30:42 
	 * @author: chengxg
	 */
	public void savePoint(Point point){
		pointMapper.insert(point);
	}
	
	/**
	 * 查询所有的积分规则
	 * @Title: getAllPoint   
	 * @param merchanId
	 * @return
	 * @date:   2017年9月20日 下午9:30:30 
	 * @author: chengxg
	 */
	public List<Point> getAllPoint(Long merchanId){
		Wrapper<Point> w = new EntityWrapper<Point>();
		w.eq("status", 1);
		w.eq("merchant_id", merchanId);
		return pointMapper.selectList(w);
	}
}
