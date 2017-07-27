/*package com.ikoori.vip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ReferenceBean;

@Configuration
public class ReferenceConfig extends DubboBaseConfig {

    @Bean
    public ReferenceBean<Person> person() {
        ReferenceBean<Person> ref = new ReferenceBean<>();
        ref.setVersion("myversion");
        ref.setInterface(Person.class);
        ref.setTimeout(5000);
        ref.setRetries(3);
        ref.setCheck(false);
        return ref;
    }
}
*/