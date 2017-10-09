package com.ikoori.vip.server.modular.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.annotion.Permission;
import com.ikoori.vip.common.constant.factory.PageFactory;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Share;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.ICouponService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IShareService;

/**
 * 分享规则控制器
 *
 * @author chengxg
 * @Date 2017-10-16 14:22:05
 */
@Controller
@RequestMapping("/share")
public class ShareController extends BaseController {

	private String PREFIX = "/biz/share/";
	@Autowired
	IShareService shareService;
	@Autowired
	IMerchantService merchantService;
	@Autowired
	ICouponService couponService;

	/**
	 * 跳转到分享规则首页
	 */
	@Permission
	@RequestMapping("")
	public String index() {
		return PREFIX + "share.html";
	}

	/**
	 * 跳转到添加分享规则
	 */
	@Permission
	@RequestMapping("/share_add")
	public String shareAdd(Model model) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	
    	Map<String, Object> couponCon = new HashMap<String, Object>();
		couponCon.put("merchantId", merchant.getId());
		couponCon.put("invalid", true);
		List<Coupon> coupons  = couponService.selectByCondition(couponCon);
    	// 查询优惠群
    	model.addAttribute("coupons", coupons);
		return PREFIX + "share_add.html";
	}

	/**
	 * 跳转到修改分享规则
	 */
	@Permission
	@RequestMapping("/share_update/{shareId}")
	public String shareUpdate(@PathVariable Long shareId, Model model) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	Map<String, Object> couponCon = new HashMap<String, Object>();
		couponCon.put("merchantId", merchant.getId());
		couponCon.put("invalid", true);
		List<Coupon> coupons  = couponService.selectByCondition(couponCon);
		
		Share share = shareService.selectById(shareId);
		model.addAttribute(share);
		// 查询优惠群
    	model.addAttribute("coupons", coupons);
		return PREFIX + "share_edit.html";
	}

	/**
	 * 获取分享规则列表
	 */
	@Permission
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String condition) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
		List<Map<String, Object>> result = shareService.getShareList(page, merchant.getId(), page.getOrderByField(),
				page.isAsc());
		page.setRecords(result);
		return super.packForBT(page);
	}

	/**
	 * 新增分享规则
	 */
	@RequestMapping(value = "/add")
	@Permission
	@ResponseBody
	public Object add(Share share) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		share.setMerchantId(merchant.getId());
		shareService.saveShare(share);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除分享规则
	 */
	@RequestMapping(value = "/delete")
	@Permission
	@ResponseBody
	public Object delete(@RequestParam Long shareId) {
		shareService.deleteById(shareId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改分享规则
	 */
	@RequestMapping(value = "/update")
	@Permission
	@ResponseBody
	public Object update(Share share) {
		if (ToolUtil.isEmpty(share) || share.getId() == null) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		share.setMerchantId(merchant.getId());
		shareService.saveShare(share);
		return super.SUCCESS_TIP;
	}

	/**
	 * 分享规则详情
	 */
	@RequestMapping(value = "/detail")
	@ResponseBody
	public Object detail() {
		return null;
	}
}
