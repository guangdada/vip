package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Ticket;

/**
 * 小票Dao
 *
 * @author chengxg
 * @Date 2017-08-15 11:30:26
 */
public interface TicketDao {
   List<Map<String, Object>> getTicketList(@Param("page") Page<Ticket> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
   
   public Ticket selectByStoreNum(@Param("storeNum")  String storeNum);
}
