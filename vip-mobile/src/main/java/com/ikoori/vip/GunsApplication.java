package com.ikoori.vip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@SpringBootApplication
public class GunsApplication extends WebMvcConfigurerAdapter{

    protected final static Logger logger = LoggerFactory.getLogger(GunsApplication.class);
    
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new WechatLogin()).excludePathPatterns("/login").excludePathPatterns("/kaptcha")
				.excludePathPatterns("/global/error").excludePathPatterns("/error").addPathPatterns("/**");
	}
    
    /**
     *  微信登录过滤器
     *  判断session中用户信息是否存在
     *  如果不存在则重新向用户发起授权
     * @return
     */
    /*@Bean  
    public FilterRegistrationBean  filterRegistrationBean() {  
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
        WeChatLoginFilter httpBasicFilter = new WeChatLoginFilter();  
        registrationBean.setFilter(httpBasicFilter);  
        List<String> urlPatterns = new ArrayList<String>();  
        urlPatterns.add("/*");  
        registrationBean.setUrlPatterns(urlPatterns); 
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/static/*");
        return registrationBean;  
    }*/  
    
    /**
     * 注册微信用户授权Servlet
     * 请求该地址可以获取用户openId等基本信息
     * @return
     */
    /*@Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new WebOauth(), "/oauth/wexin");// ServletName默认值为首字母小写，即myServlet
    }*/

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
