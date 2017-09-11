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
import com.ikoori.vip.common.constant.state.CouponType;
import com.ikoori.vip.common.constant.state.CouponUseState;
import com.ikoori.vip.common.dto.CouponFetchDo;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.CouponFetch;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.ICouponFetchService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.warpper.CouponFetchWarpper;

/**
 * 领取记录控制器
 *
 * @author chengxg
 * @Date 2017-08-14 16:14:52
 */
@Controller
@RequestMapping("/couponFetch")
public class CouponFetchController extends BaseController {

    private String PREFIX = "/biz/couponFetch/";
    @Autowired
	ICouponFetchService couponFetchService;
    @Autowired
    IMerchantService merchantService;

    /**
     * 跳转到领取记录首页
     */
    @Permission
    @RequestMapping("")
    public String index(Model model) {
    	model.addAttribute("couponUseState", CouponUseState.values());
    	model.addAttribute("couponType", CouponType.values());
        return PREFIX + "couponFetch.html";
    }

    /**
     * 跳转到添加领取记录
     */
    @Permission
    @RequestMapping("/couponFetch_add")
    public String couponFetchAdd() {
        return PREFIX + "couponFetch_add.html";
    }

    /**
     * 跳转到修改领取记录
     */
    @Permission
    @RequestMapping("/couponFetch_update/{couponFetchId}")
    public String couponFetchUpdate(@PathVariable Long couponFetchId, Model model) {
    	CouponFetch couponFetch = couponFetchService.selectById(couponFetchId);
    	model.addAttribute(couponFetch);
        return PREFIX + "couponFetch_edit.html";
    }

    /**
     * 获取领取记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Permission
    public Object list(String couponName,Integer isUsed,Integer type,String mobile,String nickname) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	Page<Object> page = new PageFactory<Object>().defaultPage();
    	List<Map<String, Object>> result = couponFetchService.selectByCondition(nickname,type,mobile,isUsed,page, couponName, page.getOrderByField(), page.isAsc(),merchant.getId());
    	page.setRecords((List<Object>) new CouponFetchWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增领取记录
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(CouponFetch couponFetch) {
    	couponFetchService.insert(couponFetch);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除领取记录
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long couponFetchId) {
        couponFetchService.deleteById(couponFetchId);
        return SUCCESS_TIP;
    }


    /**
     * 修改领取记录
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(CouponFetch couponFetch) {
    	if (ToolUtil.isEmpty(couponFetch) || couponFetch.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	couponFetchService.updateById(couponFetch);
        return super.SUCCESS_TIP;
    }

    /**
     * 领取记录详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
