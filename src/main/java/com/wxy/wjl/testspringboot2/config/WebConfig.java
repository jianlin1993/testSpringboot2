package com.wxy.wjl.testspringboot2.config;

import com.wxy.wjl.testspringboot2.filter.HttpHeadFilter;
import com.wxy.wjl.testspringboot2.filter.WjlMDCFilter;
import com.wxy.wjl.testspringboot2.filter.XssAndCsrfFilter;
import com.wxy.wjl.testspringboot2.interceptor.MDCInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${ecp.filter.referer:}")
    private String refererConfig;
    @Value("${ecp.xss.filter.invalid.character:}")
    private String xssInvalidCharacter;
    @Autowired
    private Environment env;

/*
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login.jsp");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String workDir = YGECPProperty.getProperty(MBUConstants.HWORKDIR);
        String path = workDir + "/js/dict/";
        registry.addResourceHandler("/js/dict/**").addResourceLocations("file:"+path);
        String path2 = workDir + "/assets/i18n/";
        registry.addResourceHandler("/assets/i18n/**").addResourceLocations("file:"+path2);
        super.addResourceHandlers(registry);
    }
*/

    @Bean
    public FilterRegistrationBean mrHttpHeadFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("mrHttpHeadFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new HttpHeadFilter());
        return registration;
    }
    @Bean
    public FilterRegistrationBean mrXssAndCsrfFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("mrXssAndCsrfFilter");
        registration.setOrder(2);
        registration.addUrlPatterns("*.dom");
        registration.addInitParameter("refererConfig", refererConfig);
        registration.addInitParameter("xssInvalidCharacter", xssInvalidCharacter);
        registration.setFilter(new XssAndCsrfFilter());
        return registration;
    }
/*    @Bean
    public FilterRegistrationBean wjlMDCFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("wjlMDCFilter");
        registration.setOrder(3);
        registration.addUrlPatterns("/*");
        registration.setFilter(new WjlMDCFilter());
        return registration;
    }*/

    /**
     * MDC添加日志追踪 使用拦截器代替过滤器
     * 拦截器拦截路径一定要/**结尾才行
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MDCInterceptor())
                .addPathPatterns("/**");
    }

}
