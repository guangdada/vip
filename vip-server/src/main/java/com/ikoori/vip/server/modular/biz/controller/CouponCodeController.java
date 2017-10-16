package com.ikoori.vip.server.modular.biz.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.annotion.Permission;
import com.ikoori.vip.common.constant.factory.PageFactory;
import com.ikoori.vip.common.constant.state.CouponCodeStatus;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.CouponCode;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.util.PoiExcelUtil;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.ICouponCodeService;
import com.ikoori.vip.server.modular.biz.service.ICouponService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.warpper.CouponCodeWarpper;

/**
 * 券码管理控制器
 *
 * @author chengxg
 * @Date 2017-10-13 15:00:22
 */
@Controller
@RequestMapping("/couponCode")
public class CouponCodeController extends BaseController {

	private String PREFIX = "/biz/couponCode/";
	@Autowired
	ICouponCodeService couponCodeService;
	@Autowired
	IMerchantService merchantService;
	@Autowired
	ICouponService couponService;

	/**
	 * 跳转到券码管理首页
	 */
	@Permission
	@RequestMapping("")
	public String index(Model model) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Map<String, Object> couponCon = new HashMap<String, Object>();
		couponCon.put("merchantId", merchant.getId());
		couponCon.put("invalid", true);
		List<Coupon> coupons = couponService.selectByCondition(couponCon);
		// 查询优惠群
		model.addAttribute("coupons", coupons);
		model.addAttribute("codeStatus", CouponCodeStatus.values());
		return PREFIX + "couponCode.html";
	}

	/**
	 * 跳转到添加券码管理
	 */
	@Permission
	@RequestMapping("/couponCode_add")
	public String couponCodeAdd(Model model) {
		return PREFIX + "couponCode_add.html";
	}

	/**
	 * 跳转到修改券码管理
	 */
	@Permission
	@RequestMapping("/couponCode_update/{couponCodeId}")
	public String couponCodeUpdate(@PathVariable Long couponCodeId, Model model) {
		CouponCode couponCode = couponCodeService.selectById(couponCodeId);
		model.addAttribute(couponCode);
		return PREFIX + "couponCode_edit.html";
	}

	/**
	 * 获取券码管理列表
	 */
	@Permission
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String batchNo, String verifyCode, String verifyNo, Long couponId, Integer useStatus,
			Model model) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
		List<Map<String, Object>> result = couponCodeService.getCouponCodeList(page, batchNo, verifyCode, verifyNo,
				useStatus, couponId, merchant.getId(), page.getOrderByField(), page.isAsc());
		page.setRecords((List<Map<String, Object>>) new CouponCodeWarpper(result).warp());
		return super.packForBT(page);
	}

	/**
	 * 批量生成券号
	 */
	@RequestMapping(value = "/add")
	@Permission
	@ResponseBody
	public Object add(Integer num) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		String batchNo = couponCodeService.genarateCode(merchant.getId(), num);
		if (batchNo == null) {
			throw new BussinessException(500, "生成券号失败");
		}
		return batchNo;
	}

	/**
	 * 删除券码管理
	 */
	@RequestMapping(value = "/delete")
	@Permission
	@ResponseBody
	public Object delete(@RequestParam String ids) {
		if (ToolUtil.isEmpty(ids)) {
			throw new BussinessException(500, "请选择数据");
		}
		
		String [] as = ids.split(",");
		Long [] codeIds = new Long[as.length];
		for(int i = 0;i<as.length ;i ++){
			codeIds[i] = Long.valueOf(as[i]);
		}
		couponCodeService.deleteByIds(codeIds);
		return SUCCESS_TIP;
	}

	/**
	 * 根据批次号修改券号状态为“已制卡”
	 */
	@RequestMapping(value = "/update")
	@Permission
	@ResponseBody
	public Object update(@RequestParam String ids) {
		if (ToolUtil.isEmpty(ids)) {
			throw new BussinessException(500, "请选择数据");
		}
		
		String [] as = ids.split(",");
		Long [] codeIds = new Long[as.length];
		for(int i = 0;i<as.length ;i ++){
			codeIds[i] = Long.valueOf(as[i]);
		}
		
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", codeIds);
		params.put("merchantId", merchant.getId());
		params.put("useStatus", CouponCodeStatus.madecard.getCode());
		couponCodeService.updateUseStatus(params);
		return super.SUCCESS_TIP;
	}

	/**
	 * 导出
	 * 
	 * @Title: queryExcel
	 * @param request
	 * @param response
	 * @param batchNo
	 * @param verifyCode
	 * @param verifyNo
	 * @param couponId
	 * @param useStatus
	 * @return
	 * @throws IOException
	 * @date: 2017年10月15日 下午4:03:52
	 * @author: chengxg
	 */
	@Permission
	@RequestMapping(value = "/export",method = RequestMethod.GET)
	public void export(HttpServletRequest request, HttpServletResponse response, String batchNo, String verifyCode,
			String verifyNo, Long couponId, Integer useStatus) throws IOException {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);

		Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().bigPage();
		List<Map<String, Object>> result = couponCodeService.getCouponCodeList(page, batchNo, verifyCode, verifyNo,
				useStatus, couponId, merchant.getId(), page.getOrderByField(), page.isAsc());

		List<Map<String, Object>> couponCodeList = (List<Map<String, Object>>) (new CouponCodeWarpper(result).warp());

		// 定义导出excel的名字
		String excelName = "券号批量导出表";
		if (StringUtils.isNotBlank(batchNo)) {
			excelName = excelName + "-" + batchNo;
		}
		// 获取需要转出的excle表头的map字段
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
		fieldMap.put("batchNo", "批次号");
		if (couponId != null) {
			fieldMap.put("couponName", "现金券名称");
		}
		fieldMap.put("verifyNo", "卡号");
		fieldMap.put("verifyCode", "券码");

		// 导出
		PoiExcelUtil.export(excelName, couponCodeList, fieldMap, response);
	}

	/**
	 * 券码管理详情
	 */
	@RequestMapping(value = "/detail")
	@ResponseBody
	public Object detail() {
		return null;
	}
}
