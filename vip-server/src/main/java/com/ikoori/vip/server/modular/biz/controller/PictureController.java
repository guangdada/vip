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
import com.ikoori.vip.common.persistence.model.Picture;
import com.ikoori.vip.common.persistence.model.PictureType;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IPictureService;
import com.ikoori.vip.server.modular.biz.service.IPictureTypeService;
import com.ikoori.vip.server.modular.biz.warpper.PictureWarpper;

/**
 * 图片控制器
 *
 * @author chengxg
 * @Date 2017-08-23 14:44:51
 */
@Controller
@RequestMapping("/picture")
public class PictureController extends BaseController {

    private String PREFIX = "/biz/picture/";
    @Autowired
	IPictureService pictureService;
    @Autowired
    IMerchantService merchantService;
    @Autowired
    IPictureTypeService pictureTypeService;

    /**
     * 跳转到图片首页
     */
    @Permission
    @RequestMapping("")
    public String index() {
        return PREFIX + "picture.html";
    }

    /**
     * 跳转到添加图片
     */
    @Permission
    @RequestMapping("/picture_add")
    public String pictureAdd(Model model) {
    	List<PictureType> picTypes = pictureTypeService.getAllPictureType();
    	model.addAttribute("picTypes",picTypes);
        return PREFIX + "picture_add.html";
    }

    /**
     * 跳转到修改图片
     */
    @Permission
    @RequestMapping("/picture_update/{pictureId}")
    public String pictureUpdate(@PathVariable Long pictureId, Model model) {
    	Picture picture = pictureService.selectById(pictureId);
    	model.addAttribute(picture);
        return PREFIX + "picture_edit.html";
    }

    /**
     * 获取图片列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	Page<Picture> page = new PageFactory<Picture>().defaultPage();
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
        List<Map<String, Object>> result = pictureService.getPictureList(page,condition,page.getOrderByField(), page.isAsc(),merchant.getId());
        page.setRecords((List<Picture>) new PictureWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增图片
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Picture picture) {
    	pictureService.insert(picture);
        return super.SUCCESS_TIP;
    }
    
    /**
     * 跳转到图片选择框
     */
    @Permission
    @RequestMapping("/picture_upload")
    public String pictureUpload(Model model) {
        return PREFIX + "picture_upload.html";
    }

    /**
     * 删除图片
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long pictureId) {
        pictureService.deleteById(pictureId);
        return SUCCESS_TIP;
    }


    /**
     * 修改图片
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Picture picture) {
    	if (ToolUtil.isEmpty(picture) || picture.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	pictureService.updateById(picture);
        return super.SUCCESS_TIP;
    }

    /**
     * 图片详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
