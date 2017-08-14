package com.ikoori.vip.server.modular.biz.controller;

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
import com.ikoori.vip.common.persistence.model.CouponTrade;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.ICouponTradeService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.warpper.CouponTradeWarpper;

/**
 * 使用记录控制器
 *
 * @author chengxg
 * @Date 2017-08-14 16:15:04
 */
@Controller
@RequestMapping("/couponTrade")
public class CouponTradeController extends BaseController {

    private String PREFIX = "/biz/couponTrade/";
    @Autowired
	ICouponTradeService couponTradeService;
    @Autowired
    IMerchantService merchantService;

    /**
     * 跳转到使用记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "couponTrade.html";
    }

    /**
     * 跳转到添加使用记录
     */
    @RequestMapping("/couponTrade_add")
    public String couponTradeAdd() {
        return PREFIX + "couponTrade_add.html";
    }

    /**
     * 跳转到修改使用记录
     */
    @RequestMapping("/couponTrade_update/{couponTradeId}")
    public String couponTradeUpdate(@PathVariable Long couponTradeId, Model model) {
    	CouponTrade couponTrade = couponTradeService.selectById(couponTradeId);
    	model.addAttribute(couponTrade);
        return PREFIX + "couponTrade_edit.html";
    }

    /**
     * 获取使用记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	/*Page<CouponTrade> page = new PageFactory<CouponTrade>().defaultPage();
    	List<Map<String, Object>> result = couponTradeService.getCouponTradeList(page, condition, page.getOrderByField(), page.isAsc(),merchant.getId());
    	page.setRecords((List<CouponTrade>) new CouponTradeWarpper(result).warp());
        return super.packForBT(page);*/
    	
    	Page<Object> page = new PageFactory<Object>().defaultPage();
    	List<Map<String, Object>> result = couponTradeService.selectByCondition(page, condition, page.getOrderByField(), page.isAsc(),merchant.getId());
    	page.setRecords((List<Object>) new CouponTradeWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增使用记录
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(CouponTrade couponTrade) {
    	couponTradeService.insert(couponTrade);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除使用记录
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long couponTradeId) {
        couponTradeService.deleteById(couponTradeId);
        return SUCCESS_TIP;
    }


    /**
     * 修改使用记录
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(CouponTrade couponTrade) {
    	if (ToolUtil.isEmpty(couponTrade) || couponTrade.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	couponTradeService.updateById(couponTrade);
        return super.SUCCESS_TIP;
    }

    /**
     * 使用记录详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
