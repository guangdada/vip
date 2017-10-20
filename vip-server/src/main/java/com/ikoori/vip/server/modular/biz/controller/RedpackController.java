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
import com.ikoori.vip.common.constant.state.RedpackType;
import com.ikoori.vip.common.constant.state.RedpackSendType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.RedpackMapper;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Redpack;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IRedpackService;
import com.ikoori.vip.server.modular.biz.warpper.RedpackWarpper;

/**
 * 红包控制器
 *
 * @author chengxg
 * @Date 2017-10-19 11:30:25
 */
@Controller
@RequestMapping("/redpack")
public class RedpackController extends BaseController {

    private String PREFIX = "/biz/redpack/";
    @Autowired
	IRedpackService redpackService;

    @Autowired
	IMerchantService merchantService;
    
    @Autowired
	RedpackMapper redpackMapper;
    /**
     * 跳转到红包首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	model.addAttribute("packType", RedpackType.values());
    	model.addAttribute("sendType", RedpackSendType.values());
        return PREFIX + "redpack.html";
    }

    /**
     * 跳转到添加红包
     */
    @RequestMapping("/redpack_add")
    public String redpackAdd(Model model) {
    	model.addAttribute("packType", RedpackType.values());
        return PREFIX + "redpack_add.html";
    }

    /**
     * 跳转到修改红包
     */
    @RequestMapping("/redpack_update/{redpackId}")
    public String redpackUpdate(@PathVariable Long redpackId, Model model) {
    	Redpack redpack = redpackService.selectById(redpackId);
    	if(redpack==null){
    		 throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
    	}
    	//model.addAttribute("amount", redpack.getAmount() == null ? "" : redpack.getAmount() / 100);
    	//model.addAttribute("minAmount", redpack.getMinAmount() == null ? "" : redpack.getMinAmount() / 100);
    	//model.addAttribute("maxAmount", redpack.getMaxAmount() == null ? "" : redpack.getMaxAmount() / 100);
    	model.addAttribute("packType", RedpackType.values());
    	model.addAttribute(redpack);
        return PREFIX + "redpack_edit.html";
    }

    /**
     * 获取红包列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String redpackName,Integer packType,Integer sendType) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		
		Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
		List<Map<String,Object>> result=redpackService.getRedpackList(page, redpackName, packType,sendType,merchant.getId(), page.getOrderByField(), page.isAsc());
		page.setRecords((List<Map<String, Object>>) new RedpackWarpper(result).warp());
		return super.packForBT(page);
    }

    /**
     * 新增红包
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Redpack redpack) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		redpack.setMerchantId(merchant.getId());
		redpackService.saveRedPack(redpack);
		return super.SUCCESS_TIP;
    }

    /**
     * 删除红包
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long redpackId) {
        redpackService.deleteById(redpackId);
        return SUCCESS_TIP;
    }


    /**
     * 修改红包
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Redpack redpack) {
    	if (ToolUtil.isEmpty(redpack) || redpack.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
	    redpackService.saveRedPack(redpack);
	    return super.SUCCESS_TIP;
    }

    /**
     * 红包详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
