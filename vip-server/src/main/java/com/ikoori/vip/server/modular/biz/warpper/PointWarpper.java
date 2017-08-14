package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.constant.state.PointType;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class PointWarpper extends BaseControllerWarpper {

    public PointWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	Integer ruleType =  (Integer)map.get("rule_type");
    	Integer points_limit = (Integer)map.get("points_limit");
    	if(PointType.PAY_MONEY.getCode() == ruleType){
    		map.put("points_limit", (points_limit/100) + "元");
    	}else if(PointType.PAY_ORDER.getCode()  == ruleType){
    		map.put("points_limit", points_limit+"笔");
    	}else if(PointType.SUBSCRIBE_WX.getCode()  == ruleType){
    		map.put("points_limit", "--");
    	}
    	map.put("ruleType", PointType.valueOf(ruleType));
    }

}
