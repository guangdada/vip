package com.ikoori.vip.server.modular.biz.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.CouponUseState;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.CouponFetch;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.util.RandomUtil;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.dao.CouponFetchDao;
import com.ikoori.vip.server.modular.biz.service.ICouponFetchService;

/**
 * 领取记录Dao
 *
 * @author chengxg
 * @Date 2017-08-14 16:14:52
 */
@Service
public class CouponFetchServiceImpl implements ICouponFetchService {
	@Autowired
	CouponFetchDao couponFetchDao;
	@Autowired
	CouponFetchMapper couponFetchMapper;
	@Autowired
	CouponMapper couponMapper;
	@Autowired
	CouponDao couponDao;
	@Override
	public Integer deleteById(Long id) {
		return couponFetchMapper.deleteById(id);
	}

	@Override
	public Integer updateById(CouponFetch couponFetch) {
		return couponFetchMapper.updateById(couponFetch);
	}

	@Override
	public CouponFetch selectById(Long id) {
		return couponFetchMapper.selectById(id);
	}

	@Override
	public Integer insert(CouponFetch couponFetch) {
		return couponFetchMapper.insert(couponFetch);
	}
	
	
	@Override
	public List<Map<String, Object>> selectByCondition(String verifyCode, String nickname, Integer type, String mobile,
			Integer isUsed, Page<Object> page, String name, String orderByField, boolean isAsc, Long merchantId) {
		return couponFetchDao.selectByCondition(verifyCode,nickname, type, mobile, isUsed, page, name, orderByField, isAsc,
				merchantId);
	}
	
	public List<Map<String, Object>> selectByMemberId(Long memberId,String storeNo){
		return couponFetchDao.selectByMemberId(memberId,storeNo);
	}
	
	@Transactional(readOnly = false)
	public String batchImportCode(Workbook wb, Coupon coupon, File tempFile) {
		// 错误信息接收器
		StringBuilder errorMsg = new StringBuilder("");
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		int totalRows = sheet.getPhysicalNumberOfRows();
		// 总列数
		int totalCells = 0;
		// 得到Excel的列数(前提是有行数)，从第二行算起
		if (totalRows >= 2 && sheet.getRow(1) != null) {
			totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
		}
		
		// 判断库存数量
		int stock = coupon.getStock();
		int rows = totalRows - 1;
		
		if(rows == 0){
			throw new BussinessException(500,"数据为空！");
		}
		if(rows > stock){
			throw new BussinessException(500,"剩余张数为"+stock+",小于当前导入数量"+rows+"");
		}
		
		List<CouponFetch> couponFetchs = new ArrayList<CouponFetch>();

		String br = "<br/>";

		// 循环Excel行数,从第二行开始。标题不入库
		for (int r = 1; r < totalRows; r++) {
			StringBuilder rowMessage = new StringBuilder("");
			Row row = sheet.getRow(r);
			if (row == null) {
				errorMsg.append(br + "第" + (r + 1) + "行数据有问题，请仔细检查！");
				continue;
			}
			CouponFetch couponFetch = new CouponFetch();

			String verifyCode = "";

			// 循环Excel的列
			for (int c = 0; c < totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					if (c == 0) {
						verifyCode = cell.getStringCellValue();
						if (StringUtils.isBlank(verifyCode)) {
							rowMessage.append("券号不能为空；");
						} else if (!verifyCode.matches(RandomUtil.coupon_code_matches)) {
							rowMessage.append("券号必须是20位的大写字母或数子；");
						} else if(checkVerifyCode(verifyCode)){
							rowMessage.append("券号已经存在；");
						}
						couponFetch.setVerifyCode(verifyCode);
					}
				} else {
					rowMessage.append("第" + (c + 1) + "列数据有问题，请仔细检查；");
				}
			}
			// 拼接每行的错误提示
			if (StringUtils.isNotBlank(rowMessage.toString())) {
				errorMsg.append(br + "第" + (r + 1) + "行，" + rowMessage.toString());
			} else {
				couponFetchs.add(couponFetch);
			}
		}

		// 删除上传的临时文件
		if (tempFile.exists()) {
			tempFile.delete();
		}
		
		// 全部验证通过才导入到数据库
		if (StringUtils.isBlank(errorMsg.toString())) {
			if(couponFetchs.size() == 0){
				throw new BussinessException(500,"数据为空！");
			}
			// 更新库存
			if(couponDao.updateStock(coupon.getId(), couponFetchs.size()) == 0){
				throw new BussinessException(500,"剩余张数不够");
			}
			for (CouponFetch cf : couponFetchs) {
				cf.setCouponId(coupon.getId());
				cf.setAvailableValue(coupon.getOriginValue());
				cf.setExpireTime(coupon.getEndAt());
				cf.setValidTime(coupon.getStartAt());
				cf.setIsInvalid(true);
				cf.setIsUsed(CouponUseState.NO_USED.getCode());
				cf.setMerchantId(coupon.getMerchantId());
				cf.setMessage("谢谢关注！");
				cf.setValue(coupon.getOriginValue());
				//cf.setStoreId(coupon.getStoreId());
				cf.setUsedValue(0);
				couponFetchMapper.insert(cf);
			}
			errorMsg.append("导入成功，共" + couponFetchs.size() + "条数据！");
		}else{
			throw new BussinessException(500,errorMsg.toString());
		}
		return errorMsg.toString();
	}
	
	/**
	 * 判断券码是否已存在
	 * @Title: checkVerifyCode   
	 * @param code
	 * @return
	 * @date:   2017年9月15日 下午5:50:56 
	 * @author: chengxg
	 */
	public boolean checkVerifyCode(String code){
		Wrapper<CouponFetch> w = new EntityWrapper<CouponFetch>();
		w.eq("verify_code", code);
		return couponFetchMapper.selectCount(w) == 0 ? false : true;
	}
	
	public boolean saveCouponFetch(Member member, Coupon coupon,String verifyCode) {
		CouponFetch couponFetch = new CouponFetch();
		couponFetch.setMemberId(member.getId());
		couponFetch.setCouponId(coupon.getId());
		couponFetch.setAvailableValue(coupon.getOriginValue());
		couponFetch.setExpireTime(coupon.getEndAt());
		couponFetch.setValidTime(coupon.getStartAt());
		couponFetch.setIsInvalid(true);
		couponFetch.setMobile(member.getMobile());
		couponFetch.setIsUsed(CouponUseState.NO_USED.getCode());
		couponFetch.setMerchantId(coupon.getMerchantId());
		couponFetch.setMessage("谢谢关注！");
		couponFetch.setVerifyCode(verifyCode == null ? RandomUtil.generateCouponCode() : verifyCode);
		couponFetch.setValue(coupon.getOriginValue());
		couponFetch.setUsedValue(0);
		int count = couponFetchMapper.insert(couponFetch);
		return count == 0 ? false : true;
	}
}
