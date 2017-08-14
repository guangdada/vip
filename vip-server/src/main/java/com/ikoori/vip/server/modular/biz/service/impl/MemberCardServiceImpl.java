package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.server.modular.biz.dao.MemberCardDao;
import com.ikoori.vip.server.modular.biz.service.IMemberCardService;

/**
 * 领取记录Dao
 *
 * @author chengxg
 * @Date 2017-08-14 13:14:55
 */
@Service
public class MemberCardServiceImpl implements IMemberCardService {
	@Autowired
	MemberCardDao memberCardDao;
	@Autowired
	MemberCardMapper memberCardMapper;
	@Override
	public Integer deleteById(Long id) {
		return memberCardMapper.deleteById(id);
	}

	@Override
	public Integer updateById(MemberCard memberCard) {
		return memberCardMapper.updateById(memberCard);
	}

	@Override
	public MemberCard selectById(Long id) {
		return memberCardMapper.selectById(id);
	}

	@Override
	public Integer insert(MemberCard memberCard) {
		return memberCardMapper.insert(memberCard);
	}
	
	@Override
	public List<Map<String, Object>> getMemberCardList(Page<MemberCard> page, String name, String orderByField,
			boolean isAsc,Long merchantId,String cardNumber) {
		return memberCardDao.getMemberCardList(page, name, orderByField, isAsc,merchantId,cardNumber);
	}
}
