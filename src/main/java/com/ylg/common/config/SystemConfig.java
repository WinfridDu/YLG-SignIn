package com.ylg.common.config;

import com.ylg.common.interceptor.JwtInterceptor;
import com.ylg.common.resolver.LoginUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class SystemConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Autowired
    private LoginUserHandlerMethodArgumentResolver resolver;

    /**
     * 添加拦截器的配置
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //1.添加自定义拦截器
        registry.addInterceptor(jwtInterceptor).
                addPathPatterns("/**");//2.指定拦截器的url地址
//                excludePathPatterns("/sys/login","/frame/register/**");//3.指定不拦截的url地址
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(resolver);
    }

}
