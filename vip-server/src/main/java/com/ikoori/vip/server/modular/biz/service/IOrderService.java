package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.dto.OrderPayDo;
import com.ikoori.vip.common.persistence.model.Order;

/**
 * 订单Service
 *
 * @author chengxg
 * @Date 2017-08-26 17:44:40
 */
public interface IOrderService {
	public Integer deleteById(Long id);
	public Integer updateById(Order order);
	public Order selectById(Long id);
	public Integer insert(Order order);
	List<Map<String, Object>> getOrderList(Page<Map<String, Object>> page, String memName, String orderByField,
			boolean isAsc, Long merchantId, Long storeId, String mobile, Long orderSource, String orderNo);
	public void saveOrder(OrderPayDo orderPayDo) throws Exception;
}
