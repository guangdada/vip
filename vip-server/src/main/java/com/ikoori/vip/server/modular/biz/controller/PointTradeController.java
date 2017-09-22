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
import com.ikoori.vip.common.constant.state.PointTradeType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.PointTrade;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.service.IPointService;
import com.ikoori.vip.server.modular.biz.service.IPointTradeService;
import com.ikoori.vip.server.modular.biz.warpper.PointTradeWarpper;

/**
 * 积分明细控制器
 *
 * @author chengxg
 * @Date 2017-09-20 16:36:35
 */
@Controller
@RequestMapping("/pointTrade")
public class PointTradeController extends BaseController {

	private String PREFIX = "/biz/pointTrade/";
	@Autowired
	IPointTradeService pointTradeService;
	@Autowired
	IMerchantService merchantService;
	@Autowired
	IPointService pointService;

	/**
	 * 跳转到积分明细首页
	 */
	@RequestMapping("")
	public String index(Model model) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		model.addAttribute("points", pointService.getAllPoint(merchant.getId()));
		
		model.addAttribute("tradeTypes", PointTradeType.values());
		return PREFIX + "pointTrade.html";
	}

	/**
	 * 跳转到添加积分明细
	 */
	@RequestMapping("/pointTrade_add")
	public String pointTradeAdd() {
		return PREFIX + "pointTrade_add.html";
	}

	/**
	 * 跳转到修改积分明细
	 */
	@RequestMapping("/pointTrade_update/{pointTradeId}")
	public String pointTradeUpdate(@PathVariable Long pointTradeId, Model model) {
		PointTrade pointTrade = pointTradeService.selectById(pointTradeId);
		model.addAttribute(pointTrade);
		return PREFIX + "pointTrade_edit.html";
	}

	/**
	 * 获取积分明细列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String nickname, String mobile, Integer inOut, Long pointId, Integer tradeType) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
		List<Map<String, Object>> result = pointTradeService.getPointTradeList(page, nickname, mobile, inOut, pointId,
				merchant.getId(), tradeType, page.getOrderByField(), page.isAsc());
		page.setRecords((List<Map<String, Object>>) new PointTradeWarpper(result).warp());
		return super.packForBT(page);
	}

	/**
	 * 新增积分明细
	 */
	@RequestMapping(value = "/add")
	@Permission
	@ResponseBody
	public Object add(PointTrade pointTrade) {
		pointTradeService.insert(pointTrade);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除积分明细
	 */
	@RequestMapping(value = "/delete")
	@Permission
	@ResponseBody
	public Object delete(@RequestParam Long pointTradeId) {
		pointTradeService.deleteById(pointTradeId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改积分明细
	 */
	@RequestMapping(value = "/update")
	@Permission
	@ResponseBody
	public Object update(PointTrade pointTrade) {
		if (ToolUtil.isEmpty(pointTrade) || pointTrade.getId() == null) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		pointTradeService.updateById(pointTrade);
		return super.SUCCESS_TIP;
	}

	/**
	 * 积分明细详情
	 */
	@RequestMapping(value = "/detail")
	@ResponseBody
	public Object detail() {
		return null;
	}
}
