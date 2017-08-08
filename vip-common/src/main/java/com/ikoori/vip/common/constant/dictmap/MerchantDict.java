package com.ikoori.vip.common.constant.dictmap;

import com.ikoori.vip.common.constant.dictmap.base.AbstractDictMap;

/**
 * 菜单的字典
 *
 * @author fengshuonan
 * @date 2017-05-06 15:01
 */
public class MerchantDict extends AbstractDictMap {

    @Override
    public void init() {
        put("name","商户名称");
    }

    @Override
    protected void initBeWrapped() {

    }
}
