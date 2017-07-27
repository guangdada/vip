package com.ikoori.vip.server.biz;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ikoori.vip.server.base.BaseJunit;
import com.ikoori.vip.server.modular.biz.service.ITestService;

/**
 * 业务测试
 *
 * @author fengshuonan
 * @date 2017-06-23 23:12
 */
public class BizTest extends BaseJunit {

    @Autowired
    ITestService testService;

    @Test
    public void test() {
        //testService.testGuns();

        testService.testBiz();

        //testService.testAll();
    }
}
