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
import com.ikoori.vip.common.persistence.model.Order;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.modular.biz.service.IOrderService;

/**
 * 订单控制器
 *
 * @author chengxg
 * @Date 2017-08-26 17:44:40
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private String PREFIX = "/biz/order/";
    @Autowired
	IOrderService orderService;

    /**
     * 跳转到订单首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "order.html";
    }

    /**
     * 跳转到添加订单
     */
    @RequestMapping("/order_add")
    public String orderAdd() {
        return PREFIX + "order_add.html";
    }

    /**
     * 跳转到修改订单
     */
    @RequestMapping("/order_update/{orderId}")
    public String orderUpdate(@PathVariable Long orderId, Model model) {
    	Order order = orderService.selectById(orderId);
    	model.addAttribute(order);
        return PREFIX + "order_edit.html";
    }

    /**
     * 获取订单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增订单
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Order order) {
    	orderService.insert(order);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除订单
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long orderId) {
        orderService.deleteById(orderId);
        return SUCCESS_TIP;
    }


    /**
     * 修改订单
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Order order) {
    	if (ToolUtil.isEmpty(order) || order.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	orderService.updateById(order);
        return super.SUCCESS_TIP;
    }

    /**
     * 订单详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
