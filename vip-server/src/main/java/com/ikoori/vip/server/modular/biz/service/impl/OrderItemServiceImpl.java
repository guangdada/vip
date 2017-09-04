package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.OrderItemMapper;
import com.ikoori.vip.common.persistence.model.OrderItem;
import com.ikoori.vip.server.modular.biz.dao.OrderItemDao;
import com.ikoori.vip.server.modular.biz.service.IOrderItemService;

/**
 * 订单明细Dao
 *
 * @author chengxg
 * @Date 2017-08-26 17:45:39
 */
@Service
public class OrderItemServiceImpl implements IOrderItemService {
	@Autowired
	OrderItemDao orderItemDao;
	@Autowired
	OrderItemMapper orderItemMapper;
	@Override
	public Integer deleteById(Long id) {
		return orderItemMapper.deleteById(id);
	}

	@Override
	public Integer updateById(OrderItem orderItem) {
		return orderItemMapper.updateById(orderItem);
	}

	@Override
	public OrderItem selectById(Long id) {
		return orderItemMapper.selectById(id);
	}

	@Override
	public Integer insert(OrderItem orderItem) {
		return orderItemMapper.insert(orderItem);
	}
	
	@Override
	public List<Map<String, Object>> getOrderItemList(Page<OrderItem> page, String name, String orderByField,
			boolean isAsc) {
		return orderItemDao.getOrderItemList(page, name, orderByField, isAsc);
	}

	@Override
	public List<OrderItem> selectByOrderId(Long orderId) {
		Wrapper<OrderItem> w = new EntityWrapper<OrderItem>();
		w.eq("order_id", orderId);
		w.eq("status", 1);
		return orderItemMapper.selectList(w);
	}
	
	
}
