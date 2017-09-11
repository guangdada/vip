package com.ikoori.vip.server.modular.biz.controller;

import java.util.HashMap;
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
import com.ikoori.vip.common.persistence.model.Order;
import com.ikoori.vip.common.persistence.model.OrderItem;
import com.ikoori.vip.common.persistence.model.Store;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IOrderItemService;
import com.ikoori.vip.server.modular.biz.service.IOrderService;
import com.ikoori.vip.server.modular.biz.service.IStoreService;

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
    
    @Autowired
    IMerchantService merchantService;
    
    @Autowired
    IStoreService storeService;
    
    @Autowired
    IOrderItemService orderItemService;
    /**
     * 跳转到订单首页
     */
    @Permission
    @RequestMapping("")
    public String index(Model model) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	Map<String,Object> condition = new HashMap<String,Object>();
    	condition.put("merchantId", merchant.getId());
    	List<Store> stores=storeService.selectByCondition(condition);
    	//查询店铺
    	model.addAttribute("stores", stores);
        return PREFIX + "order.html";
    }

    /**
     * 跳转到添加订单
     */
    @Permission
    @RequestMapping("/order_add")
    public String orderAdd() {
        return PREFIX + "order_add.html";
    }

    /**
     * 跳转到修改订单
     */
    @Permission
    @RequestMapping("/order_update/{orderId}")
    public String orderUpdate(@PathVariable Long orderId, Model model) {
    	Order order = orderService.selectById(orderId);
    	List<OrderItem> orderItems = orderItemService.selectByOrderId(order.getId());
    	model.addAttribute("orderItems",orderItems);
    	model.addAttribute("order",order);
        return PREFIX + "order_edit.html";
    }

    /**
     * 获取订单列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
	public Object list(String memName, Long storeId, String mobile, Long orderSource, String orderNo) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
		List<Map<String, Object>> result = orderService.getOrderList(page, memName, page.getOrderByField(),
				page.isAsc(), merchant.getId(), storeId, mobile, orderSource, orderNo);
		page.setRecords(result);
		return super.packForBT(page);
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
