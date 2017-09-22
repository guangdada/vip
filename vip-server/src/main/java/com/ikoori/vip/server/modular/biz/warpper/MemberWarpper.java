package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;
import com.ikoori.vip.common.constant.state.SexType;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class MemberWarpper extends BaseControllerWarpper {

    public MemberWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	/*map.put("sexName", ConstantFactory.me().getSexName((Integer) map.get("sex")));*/
    	map.put("isActive",Boolean.valueOf(map.get("is_active").toString()) ? "已激活" :"未激活");
    	map.put("sex", map.get("sex") == null ? "" : SexType.valueOf(Integer.valueOf(map.get("sex").toString())));
    }

}
