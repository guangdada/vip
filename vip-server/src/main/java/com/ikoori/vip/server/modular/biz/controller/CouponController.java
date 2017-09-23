package com.ikoori.vip.server.modular.biz.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.annotion.Permission;
import com.ikoori.vip.common.constant.factory.PageFactory;
import com.ikoori.vip.common.constant.state.CouponType;
import com.ikoori.vip.common.constant.tips.ErrorTip;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Store;
import com.ikoori.vip.common.persistence.model.StoreCoupon;
import com.ikoori.vip.common.util.ExcelImportUtils;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.ICardService;
import com.ikoori.vip.server.modular.biz.service.ICouponFetchService;
import com.ikoori.vip.server.modular.biz.service.ICouponService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IStoreCouponService;
import com.ikoori.vip.server.modular.biz.service.IStoreService;
import com.ikoori.vip.server.modular.biz.warpper.CouponWarpper;

/**
 * 优惠券控制器
 *
 * @author chengxg
 * @Date 2017-08-04 12:20:55
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private String PREFIX = "/biz/coupon/";
	@Autowired
	ICouponService couponService;

	@Autowired
	IMerchantService merchantService;

	@Autowired
	ICardService cardService;

	@Autowired
	IStoreService storeService;
	
	@Autowired
	ICouponFetchService couponFetchService;

	@Autowired
	IStoreCouponService storeCouponService;
	
	@Autowired
	GunsProperties gunsProperties;

	/**
	 * 跳转到优惠券首页
	 */
	@Permission
	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("couponType", CouponType.values());
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("merchantId", merchant.getId());
		List<Store> stores = storeService.selectByCondition(condition);
		model.addAttribute("stores", stores);
		return PREFIX + "coupon.html";
	}

	/**
	 * 跳转到添加优惠券
	 */
	@Permission
	@RequestMapping("/coupon_add")
	public String couponAdd(Model model) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("merchantId", merchant.getId());
		List<Store> stores = storeService.selectByCondition(condition);
		// 查询店铺
		model.addAttribute("stores", stores);

		List<Card> cards = cardService.selectByCondition(condition);
		// 查询会员卡
		model.addAttribute("cards", cards);
		model.addAttribute("merchantName", merchant.getName());
		return PREFIX + "coupon_add.html";
	}

	/**
	 * 跳转到修改优惠券
	 */
	@Permission
	@RequestMapping("/coupon_update/{couponId}")
	public String couponUpdate(@PathVariable Long couponId, Model model) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("merchantId", merchant.getId());
		// 查询可用店铺
		List<StoreCoupon> storeCoupons = storeCouponService.getByCouponId(couponId);
		model.addAttribute("storeCoupons", storeCoupons);
		
		// 查询店铺
		List<Store> stores = storeService.selectByCondition(condition);
		model.addAttribute("stores", stores);

		// 查询会员卡
		List<Card> cards = cardService.selectByCondition(condition);
		model.addAttribute("cards", cards);
		Coupon coupon = couponService.selectById(couponId);
		model.addAttribute(coupon);
		return PREFIX + "coupon_edit.html";
	}

	/**
	 * 获取优惠券列表
	 */
	@RequestMapping(value = "/list")
	@Permission
	@ResponseBody
	public Object list(String couponName, Long storeId, Boolean isExpired, Boolean isInvalid, Integer type) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Page<Coupon> page = new PageFactory<Coupon>().defaultPage();
		List<Map<String, Object>> result = couponService.getCouponList(merchant.getId(), isExpired, isInvalid, type,
				storeId, page, couponName, page.getOrderByField(), page.isAsc());
		page.setRecords((List<Coupon>) new CouponWarpper(result).warp());
		return super.packForBT(page);
	}

	/**
	 * 新增优惠券
	 */
	@RequestMapping(value = "/add")
	@Permission
	@ResponseBody
	public Object add(Coupon coupon,String storeIds) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		coupon.setMerchantId(merchant.getId());
		coupon.setCreateUserId(userId);
		coupon.setStock(coupon.getTotal());
		couponService.saveCoupon(coupon,storeIds);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除优惠券
	 */
	@RequestMapping(value = "/delete")
	@Permission
	@ResponseBody
	public Object delete(@RequestParam Long couponId) {
		Coupon coupon = couponService.selectById(couponId);
		if (coupon.isIsInvalid()) {
			throw new BussinessException(500, "优惠券已经生效，不能删除");
		}
		couponService.deleteById(couponId);
		return SUCCESS_TIP;
	}

	/**
	 * 生效优惠券
	 */
	@RequestMapping(value = "/valid")
	@Permission
	@ResponseBody
	public Object valid(@RequestParam Long couponId) {
		Coupon coupon = couponService.selectById(couponId);
		if (coupon.isIsInvalid()) {
			throw new BussinessException(500, "优惠券已经生效");
		}
		coupon.setIsInvalid(true);
		couponService.updateById(coupon);
		return SUCCESS_TIP;
	}
	
	/**
	 * 跳转到导入券号
	 */
	@Permission
	@RequestMapping("/coupon_importCode/{couponId}")
	public String couponImportCode(@PathVariable Long couponId, Model model) {
		model.addAttribute("couponId",couponId);
		return PREFIX + "coupon_importCode.html";
	}

	/**
	 * 现金券导入券码
	 */
	@RequestMapping(value = "/importCode/{couponId}")
	@Permission
	@ResponseBody
	public Object importCode(@RequestParam(value = "codeFile") MultipartFile codeFile, @PathVariable Long couponId,
			HttpServletRequest request) {
		// 判断文件是否为空
		if (codeFile == null) {
			return new ErrorTip(500, "文件不能为空！");
		}
		// 获取文件名
		String fileName = codeFile.getOriginalFilename();

		// 验证文件名是否合格
		if (!ExcelImportUtils.validateExcel(fileName)) {
			return new ErrorTip(500, "文件必须是excel格式！");
		}

		// 进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
		long size = codeFile.getSize();
		if (StringUtils.isEmpty(fileName) || size == 0) {
			return new ErrorTip(500, "文件不能为空！");
		}

		Coupon coupon = couponService.selectById(couponId);
		if(coupon == null){
			return new ErrorTip(500, "优惠券没有找到！");
		}else if(coupon.getType().intValue() != CouponType.XJQ.getCode()){
			return new ErrorTip(500, "现金券才能导入券号！");
		}
		// 执行导入
		try {
			String message = batchImport(fileName, codeFile, coupon);
			return new ErrorTip(200,message);
		} catch (BussinessException e){
			log.error("",e);
			return new ErrorTip(500,e.getMessage());
		} catch (Exception e) {
			log.error("",e);
			return new ErrorTip(500,"发生未知错误，导入失败！");
		}
	}

	/**
	 * 修改优惠券
	 */
	@RequestMapping(value = "/update")
	@Permission
	@ResponseBody
	public Object update(@Valid Coupon coupon,String storeIds) {
		if (ToolUtil.isEmpty(coupon) || coupon.getId() == null) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		couponService.saveCoupon(coupon,storeIds);
		return super.SUCCESS_TIP;
	}

	/**
	 * 优惠券详情
	 */
	@RequestMapping(value = "/detail")
	@ResponseBody
	public Object detail() {
		return null;
	}

	/**
	 * 上传excel文件到临时目录后并开始解析
	 * @Title: batchImport   
	 * @param fileName
	 * @param mfile
	 * @param coupon
	 * @return
	 * @date:   2017年9月15日 下午5:30:57 
	 * @author: chengxg
	 * @throws Exception 
	 */
	public String batchImport(String fileName, MultipartFile mfile, Coupon coupon) throws Exception {
		// 初始化输入流
		InputStream is = null;
		String msg = "";
		try {
			String filename = UUID.randomUUID().toString() + ".xlsx";
			// 新建一个文件
			String fileSavePath = gunsProperties.getFileUploadPath() + filename;
			File tempFile = new File(fileSavePath);
			mfile.transferTo(tempFile);

			// 根据新建的文件实例化输入流
			is = new FileInputStream(tempFile);

			// 根据版本选择创建Workbook的方式
			Workbook wb = null;
			// 根据文件名判断文件是2003版本还是2007版本
			if (ExcelImportUtils.isExcel2007(fileName)) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = new HSSFWorkbook(is);
			}
			// 根据excel里面的内容读取信息
			msg  = couponFetchService.batchImportCode(wb, coupon, tempFile);
		}catch (BussinessException e){
			throw e;
		}catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		return msg;
	}
}
