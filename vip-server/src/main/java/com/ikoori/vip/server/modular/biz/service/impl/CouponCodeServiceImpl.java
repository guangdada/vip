package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.CouponCodeStatus;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.CouponCodeMapper;
import com.ikoori.vip.common.persistence.model.CouponCode;
import com.ikoori.vip.common.util.DateUtil;
import com.ikoori.vip.common.util.RandomUtil;
import com.ikoori.vip.server.modular.biz.dao.CouponCodeDao;
import com.ikoori.vip.server.modular.biz.service.ICouponCodeService;

/**
 * 券码管理Dao
 *
 * @author chengxg
 * @Date 2017-10-13 15:00:23
 */
@Service
public class CouponCodeServiceImpl implements ICouponCodeService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	CouponCodeDao couponCodeDao;
	@Autowired
	CouponCodeMapper couponCodeMapper;

	@Override
	public Integer deleteById(Long id) {
		return couponCodeMapper.deleteById(id);
	}

	@Override
	public Integer updateById(CouponCode couponCode) {
		return couponCodeMapper.updateById(couponCode);
	}

	@Override
	public CouponCode selectById(Long id) {
		return couponCodeMapper.selectById(id);
	}

	@Override
	public Integer insert(CouponCode couponCode) {
		return couponCodeMapper.insert(couponCode);
	}

	@Override
	public List<Map<String, Object>> getCouponCodeList(Page<Map<String, Object>> page, String batchNo,
			String verifyCode, String verifyNo, Integer useStatus, Long couponId, Long merchantId, String orderByField,
			boolean isAsc) {
		return couponCodeDao.getCouponCodeList(page, batchNo, verifyCode, verifyNo, useStatus, couponId, merchantId,
				orderByField, isAsc);
	}

	/**
	 * 生成券码
	 * 
	 * @Title: genarateCode
	 * @param merchantId
	 * @param num
	 * @date: 2017年10月14日 下午1:54:43
	 * @author: chengxg
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String genarateCode(Long merchantId, Integer num) {
		String batchNo = DateUtil.getAllTime();
		if (checkBatchNo(batchNo)) {
			log.info("batchNo重复");
			return null;
		}
		String ms = batchNo.substring(2);
		for (int i = 1; i <= num; i++) {
			CouponCode couponCode = new CouponCode();
			couponCode.setVerifyCode(RandomUtil.generateCouponCode());
			couponCode.setBatchNo(batchNo);
			couponCode.setVerifyNo(ms + RandomUtil.toFixdLengthString(i, 4));
			couponCode.setMerchantId(merchantId);
			couponCode.setUseStatus(CouponCodeStatus.create.getCode());
			couponCodeMapper.insert(couponCode);
		}
		return batchNo;
	}

	/**
	 * 根据批次号，导出结果
	 * 
	 * @Title: selectByBatchNo
	 * @param batchNo
	 * @param merchantId
	 * @param useStatus
	 * @return
	 * @date: 2017年10月15日 下午3:07:09
	 * @author: chengxg
	 */
	public List<CouponCode> selectByBatchNo(String batchNo, Long merchantId, Integer useStatus) {
		Wrapper<CouponCode> w = new EntityWrapper<>();
		w.eq("merchant_id", merchantId);
		w.eq("use_status", useStatus);
		w.eq("batch_no", batchNo);
		return couponCodeMapper.selectList(w);
	}

	/**
	 * 根据批次号，修改券号状态为“已制卡”
	 * 
	 * @Title: updateByBatchNo
	 * @param batchNo
	 * @param merchantId
	 * @date: 2017年10月14日 下午10:06:17
	 * @author: chengxg
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateByBatchNo(String batchNo, Long merchantId, Integer useStatus) {
		couponCodeDao.updateByBatchNo(batchNo, merchantId, useStatus);
	}

	/**
	 * 修改使用状态
	 * 
	 * @Title: updateUseStatus
	 * @param ids
	 * @param merchantId
	 * @param useStatus
	 * @date: 2017年10月15日 下午4:42:19
	 * @author: chengxg
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateUseStatus(Map<String, Object> params) {
		couponCodeDao.updateUseStatus(params);
	}

	/**
	 * 判断批次号是否存在
	 * 
	 * @Title: checkBatchNo
	 * @param batchNo
	 * @return
	 * @date: 2017年10月14日 下午9:44:49
	 * @author: chengxg
	 */
	public boolean checkBatchNo(String batchNo) {
		Wrapper<CouponCode> w = new EntityWrapper<CouponCode>();
		w.eq("status", 1);
		w.eq("batch_no", batchNo);
		Integer count = couponCodeMapper.selectCount(w);
		return count > 0 ? true : false;
	}

	/**
	 * 批量删除
	 * @Title: deleteByIds   
	 * @param ids
	 * @date:   2017年10月15日 下午8:10:04 
	 * @author: chengxg
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteByIds(Long[] ids) {
		for(Long id : ids){
			CouponCode code = couponCodeMapper.selectById(id);
			if(code.getUseStatus().intValue() != CouponCodeStatus.create.getCode()){
				throw new BussinessException(500, code.getVerifyNo() + "已制卡，不能删除");
			}
			couponCodeMapper.deleteById(id);
		}
	}
}
