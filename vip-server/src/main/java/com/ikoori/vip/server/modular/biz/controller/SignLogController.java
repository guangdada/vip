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
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Sign;
import com.ikoori.vip.common.persistence.model.SignLog;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.ISignLogService;
import com.ikoori.vip.server.modular.biz.service.impl.MerchantServiceImpl;

/**
 * 签到记录控制器
 *
 * @author chengxg
 * @Date 2017-09-28 21:50:31
 */
@Controller
@RequestMapping("/signLog")
public class SignLogController extends BaseController {

	private String PREFIX = "/biz/signLog/";
	@Autowired
	ISignLogService signLogService;
	@Autowired
	IMerchantService merchantService;

	/**
	 * 跳转到签到记录首页
	 */
	@Permission
	@RequestMapping("")
	public String index() {
		return PREFIX + "signLog.html";
	}

	/**
	 * 跳转到添加签到记录
	 */
	@Permission
	@RequestMapping("/signLog_add")
	public String signLogAdd() {
		return PREFIX + "signLog_add.html";
	}

	/**
	 * 跳转到修改签到记录
	 */
	@Permission
	@RequestMapping("/signLog_update/{signLogId}")
	public String signLogUpdate(@PathVariable Long signLogId, Model model) {
		SignLog signLog = signLogService.selectById(signLogId);
		model.addAttribute(signLog);
		return PREFIX + "signLog_edit.html";
	}

	/**
	 * 获取签到记录列表
	 */
	@Permission
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String uname, String nickname, String mobile, String signS, String signE) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
		List<Map<String, Object>> result = signLogService.getSignLogList(page, uname, nickname, mobile, signS, signE,
				merchant.getId(), page.getOrderByField(), page.isAsc());
		page.setRecords(result);
		return super.packForBT(page);
	}

	/**
	 * 新增签到记录
	 */
	@RequestMapping(value = "/add")
	@Permission
	@ResponseBody
	public Object add(SignLog signLog) {
		signLogService.insert(signLog);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除签到记录
	 */
	@RequestMapping(value = "/delete")
	@Permission
	@ResponseBody
	public Object delete(@RequestParam Long signLogId) {
		signLogService.deleteById(signLogId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改签到记录
	 */
	@RequestMapping(value = "/update")
	@Permission
	@ResponseBody
	public Object update(SignLog signLog) {
		if (ToolUtil.isEmpty(signLog) || signLog.getId() == null) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		signLogService.updateById(signLog);
		return super.SUCCESS_TIP;
	}

	/**
	 * 签到记录详情
	 */
	@Permission
	@RequestMapping(value = "/detail")
	@ResponseBody
	public Object detail() {
		return null;
	}
}
