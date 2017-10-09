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
import com.ikoori.vip.common.persistence.model.ShareLog;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IShareLogService;

/**
 * 分享日志控制器
 *
 * @author chengxg
 * @Date 2017-10-16 14:22:18
 */
@Controller
@RequestMapping("/shareLog")
public class ShareLogController extends BaseController {

	private String PREFIX = "/biz/shareLog/";
	@Autowired
	IShareLogService shareLogService;

	@Autowired
	IMerchantService merchantService;

	/**
	 * 跳转到分享日志首页
	 */
	@Permission
	@RequestMapping("")
	public String index() {
		return PREFIX + "shareLog.html";
	}

	/**
	 * 跳转到添加分享日志
	 */
	@Permission
	@RequestMapping("/shareLog_add")
	public String shareLogAdd() {
		return PREFIX + "shareLog_add.html";
	}

	/**
	 * 跳转到修改分享日志
	 */
	@Permission
	@RequestMapping("/shareLog_update/{shareLogId}")
	public String shareLogUpdate(@PathVariable Long shareLogId, Model model) {
		ShareLog shareLog = shareLogService.selectById(shareLogId);
		model.addAttribute(shareLog);
		return PREFIX + "shareLog_edit.html";
	}

	/**
	 * 获取分享日志列表
	 */
	@Permission
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String shareName, String receiveName, String mobile, Integer receiveStatus) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
		List<Map<String, Object>> result = shareLogService.getShareLogList(page, shareName, receiveName, receiveStatus,
				mobile, merchant.getId(), page.getOrderByField(), page.isAsc());
		page.setRecords(result);
		return super.packForBT(page);
	}

	/**
	 * 新增分享日志
	 */
	@RequestMapping(value = "/add")
	@Permission
	@ResponseBody
	public Object add(ShareLog shareLog) {
		shareLogService.insert(shareLog);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除分享日志
	 */
	@RequestMapping(value = "/delete")
	@Permission
	@ResponseBody
	public Object delete(@RequestParam Long shareLogId) {
		shareLogService.deleteById(shareLogId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改分享日志
	 */
	@RequestMapping(value = "/update")
	@Permission
	@ResponseBody
	public Object update(ShareLog shareLog) {
		if (ToolUtil.isEmpty(shareLog) || shareLog.getId() == null) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		shareLogService.updateById(shareLog);
		return super.SUCCESS_TIP;
	}

	/**
	 * 分享日志详情
	 */
	@RequestMapping(value = "/detail")
	@ResponseBody
	public Object detail() {
		return null;
	}
}
