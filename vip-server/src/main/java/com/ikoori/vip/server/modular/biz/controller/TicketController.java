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
import com.ikoori.vip.common.persistence.model.Ticket;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.modular.biz.service.ITicketService;

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

    /**
     * 跳转到小票首页
     */
    @RequestMapping("")
    public String index() {
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
    public Object list(String condition) {
        return null;
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
