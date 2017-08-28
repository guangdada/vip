package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.OrderItem;

/**
 * 订单明细Dao
 *
 * @author chengxg
 * @Date 2017-08-26 17:45:39
 */
public interface OrderItemDao {
   List<Map<String, Object>> getOrderItemList(@Param("page") Page<OrderItem> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
