package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ikoori.vip.common.persistence.dao.StorePhotoMapper;
import com.ikoori.vip.common.persistence.model.Picture;
import com.ikoori.vip.common.persistence.model.StorePhoto;
import com.ikoori.vip.server.modular.biz.dao.StorePhotoDao;
import com.ikoori.vip.server.modular.biz.service.IStorePhotoService;

/**
 * 店铺图片Dao
 *
 * @author fengshuonan
 * @Date 2017-07-31 10:00:21
 */
@Service
public class StorePhotoServiceImpl implements IStorePhotoService {
	@Autowired
	private StorePhotoMapper storePhotoMapper;
	
	@Autowired
	private StorePhotoDao storePhotoDao;
	
	@Override
	public List<StorePhoto> selectByCondition(Long storeId) {
		Wrapper<StorePhoto> wrapper = new EntityWrapper<>();
		wrapper.eq("store_id", storeId);
		wrapper.eq("status", 1);
		return storePhotoMapper.selectList(wrapper);
	}

	public List<Picture> selectStorePhoto(Long storeId){
		return storePhotoDao.selectStorePhoto(storeId);
	}

}
