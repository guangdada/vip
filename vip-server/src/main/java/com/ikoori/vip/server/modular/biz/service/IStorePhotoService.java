package com.ikoori.vip.server.modular.biz.service;

import java.util.List;

import com.ikoori.vip.common.persistence.model.Picture;
import com.ikoori.vip.common.persistence.model.StorePhoto;

/**
 * 店铺图片Service
 *
 * @author fengshuonan
 * @Date 2017-07-31 10:00:21
 */
public interface IStorePhotoService {
	public List<StorePhoto> selectByCondition(Long storeId);
	
	public List<Picture> selectStorePhoto(Long storeId);
}
