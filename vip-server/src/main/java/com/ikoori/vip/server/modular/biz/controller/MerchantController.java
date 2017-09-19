package com.ikoori.vip.server.modular.biz.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ikoori.vip.common.annotion.log.BussinessLog;
import com.ikoori.vip.common.constant.Dict;
import com.ikoori.vip.common.constant.factory.PageFactory;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.MerchantMapper;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.modular.biz.dao.MerchantDao;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.warpper.MerchantWarpper;

/**
 * 商户控制器
 *
 * @author fengshuonan
 * @Date 2017-07-28 13:09:10
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController extends BaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	IMerchantService merchantService;
	@Autowired
	MerchantDao merchantDao;
	@Autowired
	MerchantMapper merchantMapper;
	
    private String PREFIX = "/biz/merchant/";

    /**
     * 跳转到商户首页
     */
    @Permission
    @RequestMapping("")
    public String index() {
        return PREFIX + "merchant.html";
    }

    /**
     * 跳转到添加商户
     */
    @Permission
    @RequestMapping("/merchant_add")
    public String merchantAdd() {
    	log.info("merchant_add");
        return PREFIX + "merchant_add.html";
    }

    /**
     * 跳转到修改商户
     */
    @Permission
    @RequestMapping("/merchant_update/{merchantId}")
    public String merchantUpdate(@PathVariable Long merchantId, Model model) {
    	log.info("--merchantId---",merchantId);
    	try {
			Merchant merchant = merchantMapper.selectById(merchantId);
			model.addAttribute(merchant);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("",e);
		}
        return PREFIX + "merchant_edit.html";
    }

    /**
     * 获取商户列表
     */
    @Permission
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Object list(@RequestParam(name="merName",required = false) String merName) {
    	Page<Merchant> page = new PageFactory<Merchant>().defaultPage();
        List<Map<String, Object>> result = merchantDao.getMerchantList(page,merName,page.getOrderByField(), page.isAsc());
        page.setRecords((List<Merchant>) new MerchantWarpper(result).warp());
        return super.packForBT(page);
    }

    
    /**
     * 新增商户
     */
    @BussinessLog(value = "添加商户", key = "name", dict = Dict.MerchantDict)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Merchant merchant) {
    	merchantService.saveMerchant(merchant);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除商户
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Long merchantId) {
    	merchantMapper.deleteById(merchantId);
        return SUCCESS_TIP;
    }


    /**
     * 修改商户
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Merchant merchant) {
    	if (ToolUtil.isEmpty(merchant) || merchant.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	merchantService.saveMerchant(merchant);
    	//merchantMapper.updateById(merchant);
        return super.SUCCESS_TIP;
    }

    /**
     * 商户详情
     */
    @Permission
    @RequestMapping(value = "/merchant_detail/{merchantId}")
    public Object detail(@PathVariable Long merchantId, Model model) {
    	Merchant merchant = merchantMapper.selectById(merchantId);
    	model.addAttribute(merchant);
    	return PREFIX + "merchant_detail.html";
    }
}
