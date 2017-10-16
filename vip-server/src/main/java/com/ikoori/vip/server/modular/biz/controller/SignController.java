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
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Sign;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.ISignService;
import com.ikoori.vip.server.modular.biz.warpper.SignWarpper;

/**
 * 签到规则控制器
 *
 * @author chengxg
 * @Date 2017-09-28 21:50:21
 */
@Controller
@RequestMapping("/sign")
public class SignController extends BaseController {

    private String PREFIX = "/biz/sign/";
    @Autowired
	ISignService signService;
    @Autowired
    IMerchantService merchantService;

    /**
     * 跳转到签到规则首页
     */
    @Permission
    @RequestMapping("")
    public String index() {
        return PREFIX + "sign.html";
    }

    /**
     * 跳转到添加签到规则
     */
    @Permission
    @RequestMapping("/sign_add")
    public String signAdd() {
        return PREFIX + "sign_add.html";
    }

    /**
     * 跳转到修改签到规则
     */
    @Permission
    @RequestMapping("/sign_update/{signId}")
    public String signUpdate(@PathVariable Long signId, Model model) {
    	Sign sign = signService.selectById(signId);
    	model.addAttribute(sign);
        return PREFIX + "sign_edit.html";
    }

    /**
     * 获取签到规则列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
	public Object list(String condition) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Page<Sign> page = new PageFactory<Sign>().defaultPage();
		List<Map<String, Object>> result = signService.getSignList(page, null, page.getOrderByField(), page.isAsc(),
				merchant.getId());
		page.setRecords((List<Sign>) new SignWarpper(result).warp());
		return super.packForBT(page);
	}

    /**
     * 新增签到规则
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Sign sign) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
    	sign.setMerchantId(merchant.getId());
    	signService.insert(sign);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除签到规则
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long signId) {
        signService.deleteById(signId);
        return SUCCESS_TIP;
    }


    /**
     * 修改签到规则
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Sign sign) {
    	if (ToolUtil.isEmpty(sign) || sign.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	signService.updateById(sign);
        return super.SUCCESS_TIP;
    }

    /**
     * 签到规则详情
     */
    @Permission
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
