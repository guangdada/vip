package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.MemberCard;

/**
 * 领取记录Dao
 *
 * @author chengxg
 * @Date 2017-08-14 13:14:55
 */
public interface MemberCardDao {
   List<Map<String, Object>> getMemberCardList(@Param("page") Page<MemberCard> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc,@Param("merchantId") Long merchantId,@Param("cardNumber") String cardNumber);
}
