package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.warpper.BaseControllerWarpper;

/**
 * 签到规则的包装类
 *
 * @author chengxg
 * @date 2017年2月13日 下午10:47:03
 */
public class SignWarpper extends BaseControllerWarpper {

    public SignWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    }

}
