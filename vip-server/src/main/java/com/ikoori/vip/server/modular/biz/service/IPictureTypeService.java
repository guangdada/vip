package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.PictureType;

/**
 * 图片类型Service
 *
 * @author chengxg
 * @Date 2017-08-23 18:49:50
 */
public interface IPictureTypeService {
	public Integer deleteById(Long id);
	public Integer updateById(PictureType pictureType);
	public PictureType selectById(Long id);
	public Integer insert(PictureType pictureType);
	List<Map<String, Object>> getPictureTypeList(@Param("page") Page<PictureType> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
	public List<PictureType> getAllPictureType();
}
