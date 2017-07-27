package com.ikoori.vip.server.modular.system.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.warpper.BaseControllerWarpper;

/**
 * 颜色列表的包装类
 *
 * @author fengshuonan
 * @date 2017年2月19日10:59:02
 */
public class ColorWarpper extends BaseControllerWarpper {

    public ColorWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        //map.put("pName", ConstantFactory.me().getSingleRoleName((Integer) map.get("pid")));
        //map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));
    }

}
