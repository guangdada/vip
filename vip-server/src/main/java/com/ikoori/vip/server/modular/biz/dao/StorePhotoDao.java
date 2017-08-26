package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ikoori.vip.common.persistence.model.Picture;

/**
 * 店铺图片Dao
 *
 * @author fengshuonan
 * @Date 2017-07-31 10:00:20
 */
public interface StorePhotoDao {

	public List<Picture> selectStorePhoto(@Param("storeId") Long storeId);
}
