package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.OrderItem;

/**
 * 订单明细Service
 *
 * @author chengxg
 * @Date 2017-08-26 17:45:39
 */
public interface IOrderItemService {
	public Integer deleteById(Long id);
	public Integer updateById(OrderItem orderItem);
	public OrderItem selectById(Long id);
	public Integer insert(OrderItem orderItem);
	List<Map<String, Object>> getOrderItemList(Page<OrderItem> page, String name,String orderByField, boolean isAsc);
	public List<OrderItem> selectByOrderId(Long orderId);
}
