package com.javadsl.flow;

import com.javadsl.flow.interceptor.MyCustomInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new MyCustomInterceptor()).addPathPatterns("/**");
    }
}