package com.ikoori.vip.server.template;

import java.io.IOException;

import com.ikoori.vip.server.core.template.config.ContextConfig;
import com.ikoori.vip.server.core.template.engine.SimpleTemplateEngine;
import com.ikoori.vip.server.core.template.engine.base.GunsTemplateEngine;

/**
 * 测试Guns模板引擎
 *
 * @author fengshuonan
 * @date 2017-05-09 20:27
 */
public class TemplateGenerator {

    public static void main(String[] args) throws IOException {
        ContextConfig contextConfig = new ContextConfig();
        contextConfig.setBizChName("啊哈");
        contextConfig.setBizEnName("haha");
        contextConfig.setModuleName("tk");
        contextConfig.setProjectPath("D:\\tmp\\guns");

        //contextConfig.setAddPageSwitch(false);
        //contextConfig.setEditPageSwitch(false);

        GunsTemplateEngine gunsTemplateEngine = new SimpleTemplateEngine();
        gunsTemplateEngine.setContextConfig(contextConfig);
        gunsTemplateEngine.start();
    }

}
