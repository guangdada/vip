package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.PictureMapper;
import com.ikoori.vip.common.persistence.model.Picture;
import com.ikoori.vip.server.modular.biz.dao.PictureDao;
import com.ikoori.vip.server.modular.biz.service.IPictureService;

/**
 * 图片Dao
 *
 * @author chengxg
 * @Date 2017-08-23 14:44:51
 */
@Service
public class PictureServiceImpl implements IPictureService {
	@Autowired
	PictureDao pictureDao;
	@Autowired
	PictureMapper pictureMapper;
	@Override
	public Integer deleteById(Long id) {
		return pictureMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Picture picture) {
		return pictureMapper.updateById(picture);
	}

	@Override
	public Picture selectById(Long id) {
		return pictureMapper.selectById(id);
	}

	@Override
	public Integer insert(Picture picture) {
		return pictureMapper.insert(picture);
	}
	
	@Override
	public List<Map<String, Object>> getPictureList(Page<Picture> page, String name, String orderByField,
			boolean isAsc,Long merchantId) {
		return pictureDao.getPictureList(page, name, orderByField, isAsc,merchantId);
	}
}
