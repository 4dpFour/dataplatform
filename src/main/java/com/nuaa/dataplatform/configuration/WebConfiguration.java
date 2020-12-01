package com.nuaa.dataplatform.configuration;

import com.nuaa.dataplatform.interceptor.PassportInterceptor;
import com.nuaa.dataplatform.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    PassportInterceptor passportInterceptor;
    @Autowired
    SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(securityInterceptor).addPathPatterns("/url", "/url/*", "/contract", "/contract/*");
    }
}
