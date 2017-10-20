package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.constant.state.RedpackType;
import com.ikoori.vip.common.constant.state.RoleType;
import com.ikoori.vip.common.constant.state.RedpackSendType;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;
import com.ikoori.vip.server.common.constant.factory.ConstantFactory;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class RedpackWarpper extends BaseControllerWarpper {

    public RedpackWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	map.put("pack_type", RedpackType.valueOf(Integer.valueOf(map.get("pack_type").toString())));
    	map.put("send_type", RedpackSendType.valueOf(Integer.valueOf(map.get("send_type").toString())));
    }

}
