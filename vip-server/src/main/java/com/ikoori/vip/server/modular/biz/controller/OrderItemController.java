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
import com.ikoori.vip.common.persistence.model.OrderItem;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.modular.biz.service.IOrderItemService;

/**
 * 订单明细控制器
 *
 * @author chengxg
 * @Date 2017-08-26 17:45:39
 */
@Controller
@RequestMapping("/orderItem")
public class OrderItemController extends BaseController {

    private String PREFIX = "/biz/orderItem/";
    @Autowired
	IOrderItemService orderItemService;

    /**
     * 跳转到订单明细首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "orderItem.html";
    }

    /**
     * 跳转到添加订单明细
     */
    @RequestMapping("/orderItem_add")
    public String orderItemAdd() {
        return PREFIX + "orderItem_add.html";
    }

    /**
     * 跳转到修改订单明细
     */
    @RequestMapping("/orderItem_update/{orderItemId}")
    public String orderItemUpdate(@PathVariable Long orderItemId, Model model) {
    	OrderItem orderItem = orderItemService.selectById(orderItemId);
    	model.addAttribute(orderItem);
        return PREFIX + "orderItem_edit.html";
    }

    /**
     * 获取订单明细列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增订单明细
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(OrderItem orderItem) {
    	orderItemService.insert(orderItem);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除订单明细
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long orderItemId) {
        orderItemService.deleteById(orderItemId);
        return SUCCESS_TIP;
    }


    /**
     * 修改订单明细
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(OrderItem orderItem) {
    	if (ToolUtil.isEmpty(orderItem) || orderItem.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	orderItemService.updateById(orderItem);
        return super.SUCCESS_TIP;
    }

    /**
     * 订单明细详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
