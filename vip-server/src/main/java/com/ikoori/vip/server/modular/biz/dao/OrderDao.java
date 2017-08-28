package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Order;

/**
 * 订单Dao
 *
 * @author chengxg
 * @Date 2017-08-26 17:44:40
 */
public interface OrderDao {
   List<Map<String, Object>> getOrderList(@Param("page") Page<Order> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
