package com.ikoori.vip.server.modular.system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.modular.system.dao.ColorDao;
import com.ikoori.vip.server.modular.system.warpper.ColorWarpper;

/**
 * 颜色控制器
 *
 * @author fengshuonan
 * @Date 2017-07-22 16:06:28
 */
@Controller
@RequestMapping("/color")
public class ColorController extends BaseController {

    private String PREFIX = "/system/color/";
    @Autowired
    private ColorDao colorDao;

    /**
     * 跳转到颜色首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "color.html";
    }

    /**
     * 跳转到添加颜色
     */
    @RequestMapping("/color_add")
    public String colorAdd() {
        return PREFIX + "color_add.html";
    }

    /**
     * 跳转到修改颜色
     */
    @RequestMapping("/color_update/{colorId}")
    public String colorUpdate(@PathVariable Integer colorId, Model model) {
        return PREFIX + "color_edit.html";
    }

    /**
     * 获取颜色列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	 List<Map<String, Object>> colors = this.colorDao.selectColors(super.getPara("colorName"));
         return super.warpObject(new ColorWarpper(colors));
    }

    /**
     * 新增颜色
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除颜色
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改颜色
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 颜色详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
