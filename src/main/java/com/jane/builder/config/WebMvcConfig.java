package com.jane.builder.config;

import com.jane.builder.config.interceptor.InitInterceptor;
import com.jane.builder.config.interceptor.UserInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;

    @Autowired
    private InitInterceptor initInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
        .addPathPatterns("/api/**", "/app/**", "/common/**", "/core/**", "/database/**", "/ddl/**", "/menu/**", "/sql/**", "/user/**", "/version/**")
        .excludePathPatterns("/user/login");
        registry.addInterceptor(initInterceptor).addPathPatterns("/init/**");
    }
}
