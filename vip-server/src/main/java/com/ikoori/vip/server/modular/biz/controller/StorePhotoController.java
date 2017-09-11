package com.ikoori.vip.server.modular.biz.controller;

import com.ikoori.vip.common.annotion.Permission;
import com.ikoori.vip.server.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 店铺图片控制器
 *
 * @author fengshuonan
 * @Date 2017-07-31 10:00:20
 */
@Controller
@RequestMapping("/storePhoto")
public class StorePhotoController extends BaseController {

    private String PREFIX = "/biz/storePhoto/";

    /**
     * 跳转到店铺图片首页
     */
    @Permission
    @RequestMapping("")
    public String index() {
        return PREFIX + "storePhoto.html";
    }

    /**
     * 跳转到添加店铺图片
     */
    @Permission
    @RequestMapping("/storePhoto_add")
    public String storePhotoAdd() {
        return PREFIX + "storePhoto_add.html";
    }

    /**
     * 跳转到修改店铺图片
     */
    @Permission
    @RequestMapping("/storePhoto_update/{storePhotoId}")
    public String storePhotoUpdate(@PathVariable Integer storePhotoId, Model model) {
        return PREFIX + "storePhoto_edit.html";
    }

    /**
     * 获取店铺图片列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增店铺图片
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除店铺图片
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改店铺图片
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 店铺图片详情
     */
    @Permission
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
