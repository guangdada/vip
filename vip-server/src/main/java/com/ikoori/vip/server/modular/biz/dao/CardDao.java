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
   
   /**
    * 查询获取类型为“关注微信”的会员卡
    * @Title: getCardByGrantTypeAndMerchantId   
    * @date:   2017年9月14日 上午10:57:35 
    * @author: chengxg
    * @return: Card      
    * @throws
    */
   public Card getCardByGrantTypeAndMerchantId(@Param("merchatId") Long merchatId, @Param("grantType") Integer grantType);
   
   /**
    * 查询所有的"按规则"类别的会员卡，按等级升序排序后，逐个判断是否满足升级到改卡
    * @Title: selectByMerchantId   
    * @date:   2017年9月14日 上午10:56:27 
    * @author: chengxg
    * @return: List<Card>      
    * @throws
    */
   public List<Card> selectByMerchantId(@Param("merchantId") Long merchantId);
}
