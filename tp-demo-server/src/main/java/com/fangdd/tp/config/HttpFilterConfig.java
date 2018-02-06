package com.fangdd.tp.config;

import com.fangdd.tp.controller.web.BaseWebController;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
public class HttpFilterConfig {
    private static final Logger logger = LoggerFactory.getLogger(HttpFilterConfig.class);

    @Bean
    public FilterRegistrationBean traceIdHttpFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(traceIdHttpFilter());
        registration.addUrlPatterns("/*");
        registration.setName("httpFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean(name = "httpFilter")
    public Filter traceIdHttpFilter() {
        return new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {

            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                final HttpServletRequest req = (HttpServletRequest) request;
                long startTime = System.currentTimeMillis();
                chain.doFilter(request, response);
                String queryString = req.getQueryString();
                if (Strings.isNullOrEmpty(queryString)) {
                    logger.info("[{}] {} 耗时：{}", req.getMethod(), req.getRequestURI(), System.currentTimeMillis() - startTime);
                } else {
                    logger.info("[{}] {}?{} 耗时：{}", req.getMethod(), req.getRequestURI(), queryString, System.currentTimeMillis() - startTime);
                }
            }

            @Override
            public void destroy() {
                BaseWebController.modelAndViewThreadLocal.remove();
            }
        };
    }

}
