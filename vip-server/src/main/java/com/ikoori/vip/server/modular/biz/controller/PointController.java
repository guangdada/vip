package com.ikoori.vip.server.modular.biz.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.annotion.Permission;
import com.ikoori.vip.common.constant.factory.PageFactory;
import com.ikoori.vip.common.constant.state.PointType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.PointMapper;
import com.ikoori.vip.common.persistence.model.Point;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.modular.biz.service.IPointService;
import com.ikoori.vip.server.modular.biz.warpper.PointWarpper;

/**
 * 积分管理控制器
 *
 * @author fengshuonan
 * @Date 2017-07-31 09:59:02
 */
@Controller
@RequestMapping("/point")
public class PointController extends BaseController {
	@Autowired
	private IPointService pointService;
	@Autowired
	private PointMapper pointMapper;
    private String PREFIX = "/biz/point/";

    /**
     * 跳转到积分管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "point.html";
    }

    /**
     * 跳转到添加积分管理
     */
    @RequestMapping("/point_add")
    public String pointAdd() {
        return PREFIX + "point_add.html";
    }

    /**
     * 跳转到修改积分管理
     */
    @Permission
    @RequestMapping("/point_update/{pointId}")
    public String pointUpdate(@PathVariable Integer pointId, Model model) {
    	Point point = pointMapper.selectById(pointId);
    	if(point.getRuleType().intValue() == PointType.PAY_ORDER.getCode()){
    		model.addAttribute("pointsLimit1", point.getPointsLimit());
    	}else if(point.getRuleType().intValue() == PointType.PAY_MONEY.getCode()){
    		model.addAttribute("pointsLimit2", point.getPointsLimit()/100);
    	}
    	model.addAttribute(point);
        return PREFIX + "point_edit.html";
    }

    /**
     * 获取积分管理列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition) {
    	Page<Point> page = new PageFactory<Point>().defaultPage();
        List<Map<String, Object>> result = pointService.getPointList(page,condition,page.getOrderByField(), page.isAsc());
        page.setRecords((List<Point>) new PointWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增积分管理
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Point point,String pointsLimitTemp) {
    	initParam(point, pointsLimitTemp);
    	pointService.savePoint(point);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除积分管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Long pointId) {
    	pointMapper.deleteById(pointId);
        return SUCCESS_TIP;
    }


    /**
     * 修改积分管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Point point,String pointsLimitTemp) {
    	if (ToolUtil.isEmpty(point) || point.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	initParam(point, pointsLimitTemp);
    	pointMapper.updateById(point);
        return super.SUCCESS_TIP;
    }

	private void initParam(Point point, String pointsLimitTemp) {
		if(point.getRuleType().intValue() == PointType.PAY_ORDER.getCode()){
    		point.setPointsLimit(Integer.valueOf(pointsLimitTemp));
    		point.setName(PointType.PAY_ORDER.getMessage() + pointsLimitTemp + "笔，奖励分值：" +point.getPoints());
    	}else if(point.getRuleType().intValue() == PointType.PAY_MONEY.getCode()){
    		Double cny = Double.parseDouble(pointsLimitTemp) * 100 ;//转换成Double
    		point.setPointsLimit(cny.intValue());
    		point.setName(PointType.PAY_MONEY.getMessage() + pointsLimitTemp + "元，奖励分值：" +point.getPoints());
    	}else{
    		point.setPointsLimit(0);
    		point.setName(PointType.SUBSCRIBE_WX.getMessage() + "，奖励分值：" +point.getPoints());
    	}
	}

    /**
     * 积分管理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
