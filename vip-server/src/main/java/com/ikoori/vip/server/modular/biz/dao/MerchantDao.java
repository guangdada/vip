package com.ikoori.vip.server.modular.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Merchant;

/**
 * 商户Dao
 *
 * @author fengshuonan
 * @Date 2017-07-28 13:09:10
 */
public interface MerchantDao {
	/**
	 * 查询商户列表
	 * @Title: getOperationLogs   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @date:   2017年7月29日 上午10:31:12 
	 * @author: chengxg
	 * @return: List<Map<String,Object>>      
	 * @throws
	 */
    List<Map<String, Object>> getMerchantList(@Param("page") Page<Merchant> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);


}
