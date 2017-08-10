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
import com.ikoori.vip.common.persistence.model.StoreEmployee;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.modular.biz.service.IStoreEmployeeService;
import com.ikoori.vip.server.modular.biz.warpper.StoreEmployeeWarpper;

/**
 * 员工管理控制器
 *
 * @author chengxg
 * @Date 2017-08-09 11:12:10
 */
@Controller
@RequestMapping("/storeEmployee")
public class StoreEmployeeController extends BaseController {

    private String PREFIX = "/biz/storeEmployee/";
    @Autowired
	IStoreEmployeeService storeEmployeeService;

    /**
     * 跳转到员工管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "storeEmployee.html";
    }

    /**
     * 跳转到添加员工管理
     */
    @RequestMapping("/storeEmployee_add")
    public String storeEmployeeAdd() {
        return PREFIX + "storeEmployee_add.html";
    }

    /**
     * 跳转到修改员工管理
     */
    @RequestMapping("/storeEmployee_update/{storeEmployeeId}")
    public String storeEmployeeUpdate(@PathVariable Long storeEmployeeId, Model model) {
    	StoreEmployee storeEmployee = storeEmployeeService.selectById(storeEmployeeId);
    	model.addAttribute(storeEmployee);
        return PREFIX + "storeEmployee_edit.html";
    }

    /**
     * 获取员工管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
	public Object list(String condition) {
		Page<StoreEmployee> page = new PageFactory<StoreEmployee>().defaultPage();
		List<Map<String, Object>> result = storeEmployeeService.getStoreEmployeeList(page, condition,
				page.getOrderByField(), page.isAsc());
		page.setRecords((List<StoreEmployee>) new StoreEmployeeWarpper(result).warp());
		return super.packForBT(page);
	}

    /**
     * 新增员工管理
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(StoreEmployee storeEmployee) {
    	storeEmployeeService.insert(storeEmployee);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除员工管理
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long storeEmployeeId) {
        storeEmployeeService.deleteById(storeEmployeeId);
        return SUCCESS_TIP;
    }


    /**
     * 修改员工管理
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(StoreEmployee storeEmployee) {
    	if (ToolUtil.isEmpty(storeEmployee) || storeEmployee.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	storeEmployeeService.updateById(storeEmployee);
        return super.SUCCESS_TIP;
    }

    /**
     * 员工管理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
