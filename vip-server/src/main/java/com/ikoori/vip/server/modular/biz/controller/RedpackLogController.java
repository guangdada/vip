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
import com.ikoori.vip.common.constant.state.RedPackSendStatus;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Redpack;
import com.ikoori.vip.common.persistence.model.RedpackLog;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IRedpackLogService;
import com.ikoori.vip.server.modular.biz.service.IRedpackService;
import com.ikoori.vip.server.modular.biz.warpper.RedpackLogWarpper;

/**
 * 红包记录控制器
 *
 * @author chengxg
 * @Date 2017-10-19 15:52:13
 */
@Controller
@RequestMapping("/redpackLog")
public class RedpackLogController extends BaseController {

	private String PREFIX = "/biz/redpackLog/";
	@Autowired
	IRedpackLogService redpackLogService;
	@Autowired
	IRedpackService redpackService;

	@Autowired
	IMerchantService merchantService;

	/**
	 * 跳转到红包记录首页
	 */
	@RequestMapping("")
	@Permission
	public String index(Model model) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		List<Redpack> redpacks = redpackService.selectByMerchantId(merchant.getId());
		model.addAttribute("sendStatus", RedPackSendStatus.values());
		model.addAttribute("redpacks", redpacks);
		return PREFIX + "redpackLog.html";
	}

	/**
	 * 跳转到添加红包记录
	 */
	@RequestMapping("/redpackLog_add")
	@Permission
	public String redpackLogAdd() {
		return PREFIX + "redpackLog_add.html";
	}

	/**
	 * 跳转到修改红包记录
	 */
	@RequestMapping("/redpackLog_update/{redpackLogId}")
	@Permission
	public String redpackLogUpdate(@PathVariable Long redpackLogId, Model model) {
		RedpackLog redpackLog = redpackLogService.selectById(redpackLogId);
		model.addAttribute(redpackLog);
		return PREFIX + "redpackLog_edit.html";
	}

	/**
	 * 获取红包记录列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	@Permission
	public Object list(String billno, String openid, Integer sendStatus, String sendS, String sendE,
			Integer redpackId) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
		List<Map<String, Object>> result = redpackLogService.getRedpackLogList(page, merchant.getId(), billno, openid,
				sendStatus, sendS, sendE, redpackId, page.getOrderByField(), page.isAsc());
		page.setRecords((List<Map<String, Object>>) new RedpackLogWarpper(result).warp());
		return super.packForBT(page);
	}

	/**
	 * 新增红包记录
	 */
	@RequestMapping(value = "/add")
	@Permission
	@ResponseBody
	public Object add(RedpackLog redpackLog) {
		redpackLogService.insert(redpackLog);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除红包记录
	 */
	@RequestMapping(value = "/delete")
	@Permission
	@ResponseBody
	public Object delete(@RequestParam Long redpackLogId) {
		redpackLogService.deleteById(redpackLogId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改红包记录
	 */
	@RequestMapping(value = "/update")
	@Permission
	@ResponseBody
	public Object update(RedpackLog redpackLog) {
		if (ToolUtil.isEmpty(redpackLog) || redpackLog.getId() == null) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		redpackLogService.updateById(redpackLog);
		return super.SUCCESS_TIP;
	}

	/**
	 * 红包记录详情
	 */
	@RequestMapping(value = "/detail")
	@ResponseBody
	public Object detail() {
		return null;
	}
}
