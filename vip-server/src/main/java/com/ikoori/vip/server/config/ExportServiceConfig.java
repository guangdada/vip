/*package com.ikoori.vip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ServiceBean;

@Configuration
public class ExportServiceConfig extends DubboBaseConfig {
    @Bean
    public ServiceBean<Person> personServiceExport(Person person) {
        ServiceBean<Person> serviceBean = new ServiceBean<Person>();
        serviceBean.setProxy("javassist");
        serviceBean.setVersion("myversion");
        serviceBean.setInterface(Person.class.getName());
        serviceBean.setRef(person);
        serviceBean.setTimeout(5000);
        serviceBean.setRetries(3);
        return serviceBean;
    }

}*/