package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.PictureType;

/**
 * 图片类型Dao
 *
 * @author chengxg
 * @Date 2017-08-23 18:49:50
 */
public interface PictureTypeDao {
   List<Map<String, Object>> getPictureTypeList(@Param("page") Page<PictureType> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
