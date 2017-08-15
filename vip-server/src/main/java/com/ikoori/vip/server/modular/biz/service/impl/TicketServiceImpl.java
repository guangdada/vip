package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.TicketMapper;
import com.ikoori.vip.common.persistence.model.Ticket;
import com.ikoori.vip.server.modular.biz.dao.TicketDao;
import com.ikoori.vip.server.modular.biz.service.ITicketService;

/**
 * 小票Dao
 *
 * @author chengxg
 * @Date 2017-08-15 11:30:26
 */
@Service
public class TicketServiceImpl implements ITicketService {
	@Autowired
	TicketDao ticketDao;
	@Autowired
	TicketMapper ticketMapper;
	@Override
	public Integer deleteById(Long id) {
		return ticketMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Ticket ticket) {
		return ticketMapper.updateById(ticket);
	}

	@Override
	public Ticket selectById(Long id) {
		return ticketMapper.selectById(id);
	}

	@Override
	public Integer insert(Ticket ticket) {
		return ticketMapper.insert(ticket);
	}
	
	@Override
	public List<Map<String, Object>> getTicketList(Page<Ticket> page, String name, String orderByField,
			boolean isAsc) {
		return ticketDao.getTicketList(page, name, orderByField, isAsc);
	}
}
