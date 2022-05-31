package com.nuritech.stock.mystock.common.config;

import com.nuritech.stock.mystock.common.interceptor.TokenAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenAuthenticationInterceptor tokenAuthenticationInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenAuthenticationInterceptor)
                //.addPathPatterns("/*")
                //.addPathPatterns("/sample")
                .excludePathPatterns("/api/v1/login")
                .excludePathPatterns("/authenticate")
                .excludePathPatterns("/signUp")
                .excludePathPatterns("/api/user")
                .excludePathPatterns("/api/v1/user")
                .excludePathPatterns("/refreshToken")
                .excludePathPatterns("/api/v1/oauth/refresh")
                ;

    }


}