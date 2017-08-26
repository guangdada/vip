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
import com.ikoori.vip.common.constant.state.CouponType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Store;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.ICardService;
import com.ikoori.vip.server.modular.biz.service.ICouponService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
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

    private String PREFIX = "/biz/coupon/";
    @Autowired
	ICouponService couponService;

    @Autowired
    IMerchantService merchantService;
    
    @Autowired
	ICardService cardService;
    
    @Autowired
    IStoreService storeService;
    /**
     * 跳转到优惠券首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "coupon.html";
    }

    /**
     * 跳转到添加优惠券
     */
    @RequestMapping("/coupon_add")
    public String couponAdd(Model model) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	
    	Map<String,Object> condition = new HashMap<String,Object>();
    	condition.put("merchantId", merchant.getId());
    	List<Store> stores=storeService.selectByCondition(condition);
    	//查询店铺
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
    @RequestMapping("/coupon_update/{couponId}")
    public String couponUpdate(@PathVariable Long couponId, Model model) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	Map<String,Object> condition = new HashMap<String,Object>();
    	condition.put("merchantId", merchant.getId());
    	
    	List<Store> stores=storeService.selectByCondition(condition);
    	//查询店铺
    	model.addAttribute("stores", stores);
    	
    	List<Card> cards = cardService.selectByCondition(condition);
    	// 查询会员卡
    	model.addAttribute("cards", cards);
    	Coupon coupon = couponService.selectById(couponId);
    	model.addAttribute(coupon);
        return PREFIX + "coupon_edit.html";
    }

    /**
     * 获取优惠券列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	Page<Coupon> page = new PageFactory<Coupon>().defaultPage();
        List<Map<String, Object>> result = couponService.getCouponList(page,condition,page.getOrderByField(), page.isAsc());
        page.setRecords((List<Coupon>) new CouponWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增优惠券
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
	public Object add(Coupon coupon) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
		coupon.setMerchantId(merchant.getId());
		coupon.setType(CouponType.YHQ.getCode());
		coupon.setCreateUserId(userId);
		coupon.setStock(coupon.getTotal());
		couponService.saveCoupon(coupon);
		return super.SUCCESS_TIP;
	}

    /**
     * 删除优惠券
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long couponId) {
        couponService.deleteById(couponId);
        return SUCCESS_TIP;
    }


    /**
     * 修改优惠券
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Coupon coupon) {
    	if (ToolUtil.isEmpty(coupon) || coupon.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	couponService.saveCoupon(coupon);
    	//couponService.updateById(coupon);
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
}
