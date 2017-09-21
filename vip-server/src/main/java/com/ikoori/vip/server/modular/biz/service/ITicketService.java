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
	List<Map<String, Object>> getTicketList(Page<Ticket> page, String ticketName,Long storeId,Long merchantId,String orderByField,boolean isAsc);
	public Ticket selectByStoreNum(String storeNum);
	/**
	 * 验证店铺是否已经添加了小票
	 * @Title: checkTicket   
	 * @date:   2017年9月21日 下午8:07:05 
	 * @author: huanglin
	 * @return: boolean      
	 * @throws
	 */
	public boolean checkTicket(Long id, Long storeId,Long merchantId);
}
