package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.PictureTypeMapper;
import com.ikoori.vip.common.persistence.model.PictureType;
import com.ikoori.vip.server.modular.biz.dao.PictureTypeDao;
import com.ikoori.vip.server.modular.biz.service.IPictureTypeService;

/**
 * 图片类型Dao
 *
 * @author chengxg
 * @Date 2017-08-23 18:49:50
 */
@Service
public class PictureTypeServiceImpl implements IPictureTypeService {
	@Autowired
	PictureTypeDao pictureTypeDao;
	@Autowired
	PictureTypeMapper pictureTypeMapper;
	@Override
	public Integer deleteById(Long id) {
		return pictureTypeMapper.deleteById(id);
	}

	@Override
	public Integer updateById(PictureType pictureType) {
		return pictureTypeMapper.updateById(pictureType);
	}

	@Override
	public PictureType selectById(Long id) {
		return pictureTypeMapper.selectById(id);
	}

	@Override
	public Integer insert(PictureType pictureType) {
		return pictureTypeMapper.insert(pictureType);
	}
	
	@Override
	public List<Map<String, Object>> getPictureTypeList(Page<PictureType> page, String name, String orderByField,
			boolean isAsc) {
		return pictureTypeDao.getPictureTypeList(page, name, orderByField, isAsc);
	}

	@Override
	public List<PictureType> getAllPictureType() {
		Wrapper<PictureType> wrapper = new EntityWrapper<PictureType>();
		wrapper.eq("status", 1);
		return pictureTypeMapper.selectList(wrapper);
	}
}
