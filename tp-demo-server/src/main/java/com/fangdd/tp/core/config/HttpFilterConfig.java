package com.fangdd.tp.core.config;

import com.fangdd.tp.core.config.http.HttpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpFilterConfig {
    @Bean
    public FilterRegistrationBean traceIdHttpFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpFilter());
        registration.addUrlPatterns("/*");
        registration.setName("httpFilter");
        registration.setOrder(2);
        return registration;
    }
}
