package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Card;

/**
 * 会员卡Dao
 *
 * @author chengxg
 * @Date 2017-08-04 11:07:42
 */
public interface CardDao {
   List<Map<String, Object>> getCardList(@Param("page") Page<Card> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,@Param("merchantId") Long merchantId);
   
   public Card getCardByGrantTypeAndMerchantId(@Param("merchatId") Long merchatId, @Param("grantType") Integer grantType);
}
