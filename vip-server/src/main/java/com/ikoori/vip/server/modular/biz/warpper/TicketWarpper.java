package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.constant.state.RoleType;
import com.ikoori.vip.common.constant.state.SexType;
import com.ikoori.vip.common.constant.state.SpecType;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;
import com.ikoori.vip.server.common.constant.factory.ConstantFactory;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class TicketWarpper extends BaseControllerWarpper {

    public TicketWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	int specType = (Integer)map.get("spec_type");
    	map.put("specType", SpecType.valueOf(specType));
    	if(((Integer)map.get("type"))==1){
    		map.put("type", "购物小票");
    	}else if(((Integer)map.get("type"))==2){
    		map.put("type", "退货小票");
    	}
    	Long storeId = (Long)map.get("store_id");
    	map.put("storeName", ConstantFactory.me().getStoreName(storeId));
    }

}
