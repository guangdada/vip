package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.warpper.BaseControllerWarpper;

/**
 * 订单管理的包装类
 *
 * @author chengxg
 * @date 2017年2月13日 下午10:47:03
 */
public class OrderWarpper extends BaseControllerWarpper {

    public OrderWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	Integer pay_status = (Integer) map.get("pay_status");
    	map.put("pay_status", pay_status == 1 ? "支付成功" : "支付失败");
    }

}
