package com.ikoori.vip.mobile.config;

import java.util.Properties;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.ikoori.vip.common.util.xss.XssFilter;
import com.ikoori.vip.core.listener.ConfigListener;
import com.ikoori.vip.mobile.interceptor.WechatLogin;

/**
 * web 配置类
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:03:32
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    WechatLogin wechatLogin() {
        return new WechatLogin();
    }
    
    /**
	 * 微信登录拦截器
	 * 
	 * @Title: addInterceptors
	 * @param registry
	 * @date: 2017年9月16日 下午3:27:04
	 * @author: chengxg
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String [] excludePath = {"/login","/kaptcha","/global/error","/error","/xcx/**"};
		registry.addInterceptor(wechatLogin()).excludePathPatterns(excludePath).addPathPatterns("/**");
	}
	
	/**
	 * 增加swagger的支持
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("MP_verify_5Pq2JtlE1OUTmVqL.txt")
				.addResourceLocations("classpath:/META-INF/resources/");
	}

    /**
     * xssFilter注册
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new XssFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * RequestContextListener注册
     */
    @Bean
    public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerRegistration() {
        return new ServletListenerRegistrationBean<>(new RequestContextListener());
    }

    /**
     * ConfigListener注册
     */
    @Bean
    public ServletListenerRegistrationBean<ConfigListener> configListenerRegistration() {
        return new ServletListenerRegistrationBean<>(new ConfigListener());
    }

    /**
     * 验证码生成相关
     */
    @Bean
    public DefaultKaptcha kaptcha() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.border.color", "105,179,90");
        properties.put("kaptcha.textproducer.font.color", "blue");
        properties.put("kaptcha.image.width", "400");
        properties.put("kaptcha.image.height", "170");
        properties.put("kaptcha.textproducer.font.size", "150");
        properties.put("kaptcha.textproducer.char.string", "0123456789");
        properties.put("kaptcha.session.key", "code");
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
