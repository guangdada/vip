package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Picture;

/**
 * 图片Service
 *
 * @author chengxg	
 * @Date 2017-08-23 14:44:51
 */
public interface IPictureService {
	public Integer deleteById(Long id);
	public Integer updateById(Picture picture);
	public Picture selectById(Long id);
	public Integer insert(Picture picture);
	List<Map<String, Object>> getPictureList(Page<Picture> page, String name,String orderByField,boolean isAsc,Long merchantId);
	
}
