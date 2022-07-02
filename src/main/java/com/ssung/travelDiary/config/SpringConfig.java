package com.ssung.travelDiary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class SpringConfig {

    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        HiddenHttpMethodFilter httpMethodFilter = new HiddenHttpMethodFilter();
        return httpMethodFilter;
    }
}
