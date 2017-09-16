package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.constant.state.CardGrantType;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;

/**
 * 用户管理的包装类
 *
 * @author chengxg
 * @date 2017年2月13日 下午10:47:03
 */
public class CardWarpper extends BaseControllerWarpper {

    public CardWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	Integer grantType = (Integer) map.get("grant_type");
    	map.put("grant_type", CardGrantType.valueOf(grantType));
    }

}
