package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.warpper.BaseControllerWarpper;

/**
 * 积分明细
 * @ClassName:  PointTradeWarpper
 * @author: chengxg
 * @date:   2017年9月20日 下午8:57:02
 */
public class PointTradeWarpper extends BaseControllerWarpper {

    public PointTradeWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	Boolean inOut =Boolean.valueOf(map.get("in_out").toString());
    	map.put("inOut", inOut ? "收入" : "支出");
    }

}
