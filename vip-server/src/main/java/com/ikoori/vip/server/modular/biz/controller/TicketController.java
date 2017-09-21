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
import com.ikoori.vip.common.constant.Const;
import com.ikoori.vip.common.constant.factory.PageFactory;
import com.ikoori.vip.common.constant.state.SpecType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.TicketMapper;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Store;
import com.ikoori.vip.common.persistence.model.Ticket;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IStoreService;
import com.ikoori.vip.server.modular.biz.service.ITicketService;
import com.ikoori.vip.server.modular.biz.warpper.TicketWarpper;

/**
 * 小票控制器
 *
 * @author chengxg
 * @Date 2017-08-15 11:30:26
 */
@Controller
@RequestMapping("/ticket")
public class TicketController extends BaseController {

    private String PREFIX = "/biz/ticket/";
    @Autowired
	ITicketService ticketService;
    @Autowired
   	TicketMapper ticketMapper;
    @Autowired
   	IMerchantService merchantService;
    @Autowired
    IStoreService storeService;
    
    /**
     * 跳转到小票首页
     */
    @Permission
    @RequestMapping("")
    public String index(Model model) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("merchantId", merchant.getId());
		List<Store> stores = storeService.selectByCondition(condition);
		// 查询店铺
		model.addAttribute("stores", stores);
    	
		/*
		 * Long userId = Long.valueOf(ShiroKit.getUser().getId()); Ticket
		 * ticket=ticketMapper.selectById(1); model.addAttribute("ticket",
		 * ticket);
		 */
		return PREFIX + "ticket.html";
    }

    /**
     * 跳转到添加小票
     */
   /* @Permission*/
    @RequestMapping("/ticket_add")
    public String ticketAdd(Model model) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("merchantId", merchant.getId());
		List<Store> stores = storeService.selectByCondition(condition);
		// 查询店铺
		model.addAttribute("stores", stores);
		
		model.addAttribute("specType",SpecType.values());
        return PREFIX + "ticket_add.html";
    }

    /**
     * 验证店铺是否已经添加小票
     * @return
     */
	@RequestMapping("/checkStore")
	@Permission(Const.MERCHANT_NAME)
	@ResponseBody
	public Object checkStore(Long storeId,Long ticketId) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		/*List<Store> stores = storeService.selectStore(merchant.getId());
		if(stores!=null && !stores.isEmpty()){
			return "true";
		}else{
			return "flase";
		}*/
		return ticketService.checkTicket(ticketId, storeId, merchant.getId());
	}
    /**
     * 跳转到修改小票
     */
    @Permission
    @RequestMapping("/ticket_update/{ticketId}")
    public String ticketUpdate(@PathVariable Long ticketId, Model model) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("merchantId", merchant.getId());
		List<Store> stores = storeService.selectByCondition(condition);
		// 查询店铺
		model.addAttribute("stores", stores);
		
    	Ticket ticket = ticketService.selectById(ticketId);
    	model.addAttribute(ticket);
    	model.addAttribute("specType",SpecType.values());
        return PREFIX + "ticket_edit.html";
    }

    /**
     * 获取小票列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String ticketName,Long storeId,Model model) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
    	
		Page<Ticket> page = new PageFactory<Ticket>().defaultPage();
    	List<Map<String, Object>> result = ticketService.getTicketList(page, ticketName,storeId,merchant.getId(), page.getOrderByField(), page.isAsc());
    	page.setRecords((List<Ticket>) new TicketWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增小票
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Ticket ticket) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
    	ticket.setMerchantId(merchant.getId());
    	ticketService.insert(ticket);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除小票
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long ticketId) {
        ticketService.deleteById(ticketId);
        return SUCCESS_TIP;
    }


    /**
     * 修改小票
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Ticket ticket) {
    	if (ToolUtil.isEmpty(ticket) || ticket.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	ticketService.updateById(ticket);
        return super.SUCCESS_TIP;
    }

    /**
     * 小票详情
     */
    @Permission
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
