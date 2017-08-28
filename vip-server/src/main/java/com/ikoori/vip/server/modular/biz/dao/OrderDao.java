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
   /*查询某用户所有订单*/
   List<Map<String,Object>>selectOrderListByMemberId(@Param("memberId") Long memberId);
   /*查询某用户某订单详情*/
   List<Map<String,Object>>selectOrderDetailListByOrderId(@Param("orderId") Long orderId);
}
