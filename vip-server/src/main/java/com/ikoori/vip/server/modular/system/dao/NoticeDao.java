package com.ikoori.vip.server.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 通知dao
 *
 * @author fengshuonan
 * @date 2017-05-09 23:03
 */
public interface NoticeDao {

    List<Map<String, Object>> list(@Param("condition") String condition);
}
