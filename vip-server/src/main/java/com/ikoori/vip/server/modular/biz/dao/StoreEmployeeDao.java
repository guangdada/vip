package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.StoreEmployee;

/**
 * 员工管理Dao
 *
 * @author chengxg
 * @Date 2017-08-09 11:12:10
 */
public interface StoreEmployeeDao {
   List<Map<String, Object>> getStoreEmployeeList(@Param("page") Page<Map<String, Object>> page, @Param("employeeName") String employeeName,@Param("mobile") String mobile,@Param("sex")Integer sex,@Param("storeId")Long storeId,@Param("roleId") Long roleId,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
