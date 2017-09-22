package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
	public List<Map<String, Object>> getTicketList(Page<Ticket> page, String ticketName,Long storeId,Long merchantId, String orderByField,
			boolean isAsc) {
		return ticketDao.getTicketList(page, ticketName,storeId,merchantId, orderByField, isAsc);
	}
	
	public Ticket selectByStoreNum(String storeNum){
		return ticketDao.selectByStoreNum(storeNum);
	}
	
	/**   
	 * @Title: checkTicket 
	 * @Description: 检查 店铺是否已有小票
	 * @date:   2017年9月22日 上午10:49:27 
	 * @author: huanglin    
	 * @throws   
	 */  
	public boolean checkTicket(Long id, Long storeId,Long merchantId) {
		Wrapper<Ticket> ticket = new EntityWrapper<>();
		ticket.eq("merchant_id", merchantId);
		ticket.eq("status", 1);
		if (id != null) {
			ticket.ne("id", id);
		}
		if (storeId != null) {
			ticket.eq("store_id", storeId);
		}
		return ticketMapper.selectCount(ticket) == 0;
	}
}
