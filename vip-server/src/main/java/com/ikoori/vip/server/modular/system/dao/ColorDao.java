package com.ikoori.vip.server.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 颜色Dao
 *
 * @author fengshuonan
 * @Date 2017-07-22 16:06:28
 */
public interface ColorDao {
	/**
     * 根据条件查询颜色列表
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectColors(@Param("condition") String condition);
}
