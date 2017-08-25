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
public class PictureWarpper extends BaseControllerWarpper {

    public PictureWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	Long pictypeId =  (Long)map.get("pictype_id");
    	//map.put("picType", ConstantFactory.me().getPictypeName(pictypeId));
    }

}
