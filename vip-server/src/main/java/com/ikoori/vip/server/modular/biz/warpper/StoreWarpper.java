package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.constant.state.StoreType;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;
/**
 * 店铺
 * @ClassName:  StoreWarpper
 * @author: chengxg
 * @date:   2017年10月12日 下午10:15:52
 */
public class StoreWarpper extends BaseControllerWarpper {

    public StoreWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	Integer storeType = (Integer) map.get("store_type");
    	map.put("storeType", StoreType.valueOf(storeType));
    }

}
