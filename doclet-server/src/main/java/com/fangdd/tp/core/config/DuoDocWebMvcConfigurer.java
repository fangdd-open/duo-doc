package com.fangdd.tp.core.config;

import com.fangdd.tp.core.config.http.AccountInterceptor;
import com.fangdd.tp.service.SiteService;
import com.fangdd.tp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xuwenzhen
 * @date 18/8/8
 */
@Configuration
public class DuoDocWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    private UserService userService;

    @Autowired
    private SiteService siteService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AccountInterceptor accountInterceptor = new AccountInterceptor();
        accountInterceptor.setUserService(userService);
        accountInterceptor.setSiteService(siteService);
        registry.addInterceptor(accountInterceptor).addPathPatterns("/**");
    }
}
