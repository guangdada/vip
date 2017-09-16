package com.ikoori.vip.server.modular.biz.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.CouponFetch;

/**
 * 领取记录Service
 *
 * @author chengxg
 * @Date 2017-08-14 16:14:52
 */
public interface ICouponFetchService {
	public Integer deleteById(Long id);

	public Integer updateById(CouponFetch couponFetch);

	public CouponFetch selectById(Long id);

	public Integer insert(CouponFetch couponFetch);

	/**
	 * 分页查询
	 * @Title: selectByCondition   
	 * @param verifyCode
	 * @param nickname
	 * @param type
	 * @param mobile
	 * @param isUsed
	 * @param page
	 * @param name
	 * @param orderByField
	 * @param isAsc
	 * @param merchantId
	 * @return
	 * @date:   2017年9月15日 下午5:27:23 
	 * @author: chengxg
	 */
	public List<Map<String, Object>> selectByCondition(String verifyCode, String nickname, Integer type, String mobile,
			Integer isUsed, Page<Object> page, String name, String orderByField, boolean isAsc, Long merchantId);

	/**
	 * 根据会员id查询领取的券
	 * @Title: selectByMemberId   
	 * @param memberId
	 * @return
	 * @date:   2017年9月15日 下午5:26:43 
	 * @author: chengxg
	 */
	public List<Map<String, Object>> selectByMemberId(Long memberId);
	
	/**
	 * 现金券批量导入券号
	 * @Title: batchImportCode   
	 * @param wb
	 * @param coupon
	 * @param tempFile
	 * @return
	 * @date:   2017年9月15日 下午5:25:59 
	 * @author: chengxg
	 */
	public String batchImportCode(Workbook wb, Coupon coupon, File tempFile);
}
