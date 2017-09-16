package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.constant.state.CardGrantType;
import com.ikoori.vip.common.constant.state.MemCardState;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;
import com.ikoori.vip.server.common.constant.factory.ConstantFactory;

/**
 * 用户管理的包装类
 *
 * @author chengxg
 * @date 2017年2月13日 下午10:47:03
 */
public class MemberCardWarpper extends BaseControllerWarpper {

    public MemberCardWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	String grantType = map.get("grant_type").toString();
    	String state = map.get("state").toString();
    	map.put("grantType", grantType == null ? "-" : CardGrantType.valueOf(Integer.valueOf(grantType)));
    	map.put("state", state == null ? "-" : MemCardState.valueOf(Integer.valueOf(state)));
    }

}
