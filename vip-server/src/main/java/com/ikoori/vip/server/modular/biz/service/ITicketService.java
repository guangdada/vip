package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Ticket;

/**
 * 小票Service
 *
 * @author chengxg
 * @Date 2017-08-15 11:30:26
 */
public interface ITicketService {
	public Integer deleteById(Long id);
	public Integer updateById(Ticket ticket);
	public Ticket selectById(Long id);
	public Integer insert(Ticket ticket);
	List<Map<String, Object>> getTicketList(Page<Ticket> page, String name,String orderByField,boolean isAsc);
	public Ticket selectByStoreNum(String storeNum);
}
