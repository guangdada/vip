package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.constant.state.CouponType;
import com.ikoori.vip.common.constant.state.CouponUseState;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;

/**
 * 用户管理的包装类
 *
 * @author chengxg
 * @date 2017年2月13日 下午10:47:03
 */
public class CouponTradeWarpper extends BaseControllerWarpper {

    public CouponTradeWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	Integer couponType = Integer.valueOf(map.get("couponType").toString());
    	map.put("couponType", CouponType.valueOf(couponType));
    }

}
