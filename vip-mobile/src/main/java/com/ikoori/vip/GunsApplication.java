
package com.ikoori.vip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ikoori.vip.mobile.interceptor.WechatLogin;


/**
 * SpringBoot方式启动类
 *
 * @author stylefeng
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class GunsApplication extends WebMvcConfigurerAdapter{

    protected final static Logger logger = LoggerFactory.getLogger(GunsApplication.class);
    
    /**
     * 微信登录拦截器
     * @Title: addInterceptors   
     * @param registry
     * @date:   2017年9月16日 下午3:27:04 
     * @author: chengxg
     */
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new WechatLogin()).excludePathPatterns("/login").excludePathPatterns("/kaptcha")
				.excludePathPatterns("/global/error").excludePathPatterns("/error").addPathPatterns("/**");
	}

    /**
     * 增加swagger的支持
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    public static void main(String[] args) {
        SpringApplication.run(GunsApplication.class, args);
        logger.info("GunsApplication is sussess!");
    }
}
