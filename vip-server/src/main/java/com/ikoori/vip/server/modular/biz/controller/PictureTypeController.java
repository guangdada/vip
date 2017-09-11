package com.ikoori.vip.server.modular.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ikoori.vip.common.annotion.Permission;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.PictureType;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.modular.biz.service.IPictureTypeService;

/**
 * 图片类型控制器
 *
 * @author chengxg
 * @Date 2017-08-23 18:49:48
 */
@Controller
@RequestMapping("/pictureType")
public class PictureTypeController extends BaseController {

    private String PREFIX = "/biz/pictureType/";
    @Autowired
	IPictureTypeService pictureTypeService;

    /**
     * 跳转到图片类型首页
     */
    @Permission
    @RequestMapping("")
    public String index() {
        return PREFIX + "pictureType.html";
    }

    /**
     * 跳转到添加图片类型
     */
    @Permission
    @RequestMapping("/pictureType_add")
    public String pictureTypeAdd() {
        return PREFIX + "pictureType_add.html";
    }

    /**
     * 跳转到修改图片类型
     */
    @Permission
    @RequestMapping("/pictureType_update/{pictureTypeId}")
    public String pictureTypeUpdate(@PathVariable Long pictureTypeId, Model model) {
    	PictureType pictureType = pictureTypeService.selectById(pictureTypeId);
    	model.addAttribute(pictureType);
        return PREFIX + "pictureType_edit.html";
    }

    /**
     * 获取图片类型列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增图片类型
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(PictureType pictureType) {
    	pictureTypeService.insert(pictureType);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除图片类型
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long pictureTypeId) {
        pictureTypeService.deleteById(pictureTypeId);
        return SUCCESS_TIP;
    }


    /**
     * 修改图片类型
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(PictureType pictureType) {
    	if (ToolUtil.isEmpty(pictureType) || pictureType.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	pictureTypeService.updateById(pictureType);
        return super.SUCCESS_TIP;
    }

    /**
     * 图片类型详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
