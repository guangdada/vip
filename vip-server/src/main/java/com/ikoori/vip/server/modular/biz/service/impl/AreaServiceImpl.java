package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ikoori.vip.common.persistence.dao.AreaMapper;
import com.ikoori.vip.common.persistence.model.Area;
import com.ikoori.vip.server.modular.biz.service.IAreaService;

/**
 * 门店Dao
 *
 * @author chengxg
 * @Date 2017-08-07 17:52:18
 */
@Service
public class AreaServiceImpl implements IAreaService {
	@Autowired
	AreaMapper areaMapper;

	@Override
	public List<Area> searchRoot() {
		Wrapper<Area> wrapper = new EntityWrapper<Area>();
		wrapper.isNull("parent_id");
		List<Area> areas = areaMapper.selectList(wrapper);
		return areas;
	}

	@Override
	public List<Area> searchNext(Long parentId) {
		Wrapper<Area> wrapper = new EntityWrapper<Area>();
		wrapper.eq("parent_id", parentId);
		List<Area> areas = areaMapper.selectList(wrapper);
		return areas;
	}
}
