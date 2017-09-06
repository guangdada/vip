package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.CardRight;

/**
 * 会员权益Dao
 *
 * @author chengxg
 * @Date 2017-08-07 10:50:13
 */
public interface CardRightDao {
   List<Map<String, Object>> getCardRightList(@Param("page") Page<CardRight> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
   
   List<CardRight> selectByCardId(@Param("cardId") Long cardId);
}
