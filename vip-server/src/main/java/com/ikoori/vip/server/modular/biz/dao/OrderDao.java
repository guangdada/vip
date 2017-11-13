package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 订单Dao
 *
 * @author chengxg
 * @Date 2017-08-26 17:44:40
 */
public interface OrderDao {
	List<Map<String, Object>> getOrderList(@Param("page") Page<Map<String, Object>> page,
			@Param("memName") String memName, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,
			@Param("merchantId") Long merchantId, @Param("storeId") Long storeId, @Param("mobile") String mobile,
			@Param("orderSource") Long orderSource, @Param("orderNo") String orderNo);
  
	/*查询某用户所有订单*/
   List<Map<String,Object>>selectOrderListByMemberId(@Param("memberId") Long memberId,@Param("start") int start,@Param("pageSize") int pageSize);
   
   /*查询某用户某订单详情*/
   List<Map<String,Object>>selectOrderDetailListByOrderId(@Param("orderId") Long orderId);
   
   /**
    * 查询用户当月交易金额
    * @Title: selectMemTradeAmount   
    * @param memberId
    * @return
    * @date:   2017年11月7日 下午4:16:14 
    * @author: chengxg
    */
   Integer selectMemTradeAmount(@Param("memberId") Long memberId);
}
