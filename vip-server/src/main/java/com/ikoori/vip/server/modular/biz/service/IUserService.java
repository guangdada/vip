package com.ikoori.vip.server.modular.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.User;

/**
 * 会员卡Service
 *
 * @author chengxg
 * @Date 2017-08-04 11:07:42
 */
public interface IUserService {
	public Integer deleteById(Long id);
	public Integer updateById(User user);
	public User selectById(Long id);
	public Integer insert(User user);
	public List<User> selectByCondition(Map<String, Object> condition);
	List<Map<String, Object>> getUserList(@Param("page") Page<Card> page, @Param("name") String name,@Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
	public void saveUser(User user);
}
