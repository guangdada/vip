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
import com.ikoori.vip.common.constant.state.SpecType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.TicketMapper;
import com.ikoori.vip.common.persistence.model.Ticket;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
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
    /**
     * 跳转到小票首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	/*Merchant merchant = merchantService.getMerchantUserId(userId);
    	model.addAttribute("merchant", merchant);*/
    	/*Ticket ticket=ticketService.selectById(1L);*/
    	Ticket ticket=ticketMapper.selectById(1);
    	model.addAttribute("ticket", ticket);
    	model.addAttribute("specType", SpecType.values());
        return PREFIX + "ticket.html";
    }

    /**
     * 跳转到添加小票
     */
    @RequestMapping("/ticket_add")
    public String ticketAdd() {
        return PREFIX + "ticket_add.html";
    }

    /**
     * 跳转到修改小票
     */
    @RequestMapping("/ticket_update/{ticketId}")
    public String ticketUpdate(@PathVariable Long ticketId, Model model) {
    	Ticket ticket = ticketService.selectById(ticketId);
    	model.addAttribute(ticket);
        return PREFIX + "ticket_edit.html";
    }

    /**
     * 获取小票列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,Model model) {
    	Page<Ticket> page = new PageFactory<Ticket>().defaultPage();
    	List<Map<String, Object>> result = ticketService.getTicketList(page, condition, page.getOrderByField(), page.isAsc());
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
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
