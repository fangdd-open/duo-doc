package com.fangdd.tp.core.config.http;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author xuwenzhen
 */
@Configuration
public class HttpFilterConfig {
    @Bean
    public FilterRegistrationBean traceIdHttpFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DuoHttpLogFilter());
        registration.addUrlPatterns("/*");
        registration.setName("httpFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
