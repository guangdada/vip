package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Picture;

/**
 * 图片Dao
 *
 * @author chengxg
 * @Date 2017-08-23 14:44:51
 */
public interface PictureDao {
   List<Map<String, Object>> getPictureList(@Param("page") Page<Picture> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,@Param("merchantId") Long merchantId);
}
