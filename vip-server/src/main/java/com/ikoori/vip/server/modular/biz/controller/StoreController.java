package com.ikoori.vip.server.modular.biz.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.ikoori.vip.common.constant.state.StoreType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Store;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IStorePhotoService;
import com.ikoori.vip.server.modular.biz.service.IStoreService;
import com.ikoori.vip.server.modular.biz.warpper.StoreWarpper;

/**
 * 门店控制器
 *
 * @author chengxg
 * @Date 2017-08-07 17:52:18
 */
@Controller
@RequestMapping("/store")
public class StoreController extends BaseController {

    private String PREFIX = "/biz/store/";
    @Autowired
	IStoreService storeService;
    @Autowired
    IMerchantService merchantService;
    @Autowired
    IStorePhotoService storePhotoService;

    /**
     * 跳转到门店首页
     */
    @Permission
    @RequestMapping("")
    public String index() {
        return PREFIX + "store.html";
    }

    /**
     * 跳转到添加门店
     */
    @Permission
    @RequestMapping("/store_add")
    public String storeAdd(Model model) {
    	model.addAttribute("storeTypes", StoreType.values());
        return PREFIX + "store_add.html";
    }

    /**
     * 跳转到修改门店
     */
    @Permission
    @RequestMapping("/store_update/{storeId}")
	public String storeUpdate(@PathVariable Long storeId, Model model) {
		Store store = storeService.selectById(storeId);
		String latitude = StringUtils.isNotBlank(store.getLatitude()) ? store.getLatitude() : "";
		String longitude = StringUtils.isNotBlank(store.getLongitude()) ? store.getLongitude() : "";
		String coordinate =  longitude+ "," + latitude;
		model.addAttribute("coordinate", coordinate);
		model.addAttribute(store);
		model.addAttribute("storePhotos",storePhotoService.selectStorePhoto(storeId));
		model.addAttribute("storeTypes", StoreType.values());
		return PREFIX + "store_edit.html";
	}

    /**
     * 获取门店列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	Page<Store> page = new PageFactory<Store>().defaultPage();
    	List<Map<String, Object>> result = storeService.getStoreList(page,condition,page.getOrderByField(), page.isAsc(),merchant.getId());
    	page.setRecords((List<Store>) new StoreWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增门店
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Store store,String pics) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	store.setMerchantId(merchant.getId());
    	//storeService.insert(store);
    	storeService.saveStore(store, pics);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除门店
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long storeId) {
        storeService.deleteById(storeId);
        return SUCCESS_TIP;
    }


    /**
     * 修改门店
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Store store,String pics) {
    	if (ToolUtil.isEmpty(store) || store.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	//storeService.updateById(store);
    	storeService.saveStore(store, pics);
        return super.SUCCESS_TIP;
    }

    /**
     * 门店详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
