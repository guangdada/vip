package com.ikoori.vip.server.modular.biz.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.annotion.Permission;
import com.ikoori.vip.common.constant.factory.PageFactory;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.WxConfigMapper;
import com.ikoori.vip.common.persistence.model.WxConfig;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.modular.biz.dao.WxConfigDao;
import com.ikoori.vip.server.modular.biz.service.IWxConfigService;
import com.ikoori.vip.server.modular.biz.warpper.WxconfigWarpper;

/**
 * 公众号管理控制器
 *
 * @author fengshuonan
 * @Date 2017-07-31 09:58:11
 */
@Controller
@RequestMapping("/wxConfig")
public class WxConfigController extends BaseController {

	@Autowired
	private WxConfigDao wxConfigDao;
	
	@Autowired
	private WxConfigMapper wxConfigMapper;
	
	@Autowired
	private IWxConfigService wxConfigService;
    private String PREFIX = "/biz/wxConfig/";

    /**
     * 跳转到公众号管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "wxConfig.html";
    }

    /**
     * 跳转到添加公众号管理
     */
    @RequestMapping("/wxConfig_add")
    public String wxConfigAdd() {
        return PREFIX + "wxConfig_add.html";
    }

    /**
     * 跳转到修改公众号管理
     */
    @RequestMapping("/wxConfig_update/{wxConfigId}")
    public String wxConfigUpdate(@PathVariable Integer wxConfigId, Model model) {
    	WxConfig wxConfig = wxConfigMapper.selectById(wxConfigId);
    	model.addAttribute(wxConfig);
        return PREFIX + "wxConfig_edit.html";
    }

    /**
     * 获取公众号管理列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public Object list(String appid) {
    	Page<WxConfig> page = new PageFactory<WxConfig>().defaultPage();
        List<Map<String, Object>> result = wxConfigDao.getWxConfigList(page,appid,page.getOrderByField(), page.isAsc());
        page.setRecords((List<WxConfig>) new WxconfigWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增公众号管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Permission
    public Object add(WxConfig wxConfig) {
    	wxConfigService.saveWxConfig(wxConfig);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除公众号管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Permission
    public Object delete(Long wxConfigId) {
    	wxConfigMapper.deleteById(wxConfigId);
        return SUCCESS_TIP;
    }


    /**
     * 修改公众号管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Permission
    public Object update(WxConfig wxConfig) {
    	if (ToolUtil.isEmpty(wxConfig) || wxConfig.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	wxConfigMapper.updateById(wxConfig);
        return super.SUCCESS_TIP;
    }

    /**
     * 公众号管理详情
     */
    @RequestMapping(value = "/detail/{wxConfigId}")
    @ResponseBody
    public Object detail(@PathVariable Long wxConfigId,Model model) {
    	WxConfig wxConfig = wxConfigMapper.selectById(wxConfigId);
    	model.addAttribute(wxConfig);
        return PREFIX + "wxConfig_edit.html";
    }
}
