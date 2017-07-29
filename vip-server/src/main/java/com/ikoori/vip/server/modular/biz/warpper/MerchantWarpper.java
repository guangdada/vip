package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.warpper.BaseControllerWarpper;
import com.ikoori.vip.server.common.constant.factory.ConstantFactory;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class MerchantWarpper extends BaseControllerWarpper {

    public MerchantWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("stateName", ConstantFactory.me().getMerchantStateName((Integer) map.get("state")));
    }

}
