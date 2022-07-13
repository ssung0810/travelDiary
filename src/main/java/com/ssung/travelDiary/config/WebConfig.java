package com.ssung.travelDiary.config;

import com.ssung.travelDiary.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login/**", "/logout", "/member", "/*/api/**", "/image/**", "/css/**", "/js/**", "/images/**", "/bootstrap/**", "/*.ico", "/error", "/profile");
    }
}
