package com.ikoori.vip.server.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.alibaba.dubbo.config.spring.AnnotationBean;

/**
 * @author liuyazhuang
 * @date 2017-04-11 00:25:27
 * @description
 */
@Configuration
@Import(DubboBaseConfig.class)
@AutoConfigureAfter(DubboBaseConfig.class)
public class DubboConfiguration {
    @Bean
    // @DependsOn("dubboConfiguration")
    public AnnotationBean dubboAnnotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        // annotationBean.setApplicationContext(applicationContext);
        // annotationBean.setPackage(dubboProperties.getAnnotationPackage());
        // annotationBean.setPackage("com.roc.pay");
        return annotationBean;
    }
 
}
