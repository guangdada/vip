package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.constant.state.RedPackSendStatus;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;

/**
 * 用户管理的包装类
 *
 * @author chengxg
 * @date 2017年2月13日 下午10:47:03
 */
public class RedpackLogWarpper extends BaseControllerWarpper {

    public RedpackLogWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	Integer sendStatus = (Integer) map.get("send_status");
    	map.put("sendStatus", RedPackSendStatus.valueOf(sendStatus));
    }

}
