package com.fangdd.tp.core.config;

import com.fangdd.tp.core.annotation.Account;
import com.fangdd.tp.core.config.http.AccountInterceptor;
import com.fangdd.tp.service.SiteService;
import com.fangdd.tp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @auth ycoe
 * @date 18/8/8
 */
@Configuration
@ConditionalOnBean({Account.class, UserService.class, SiteService.class})
public class WebMvcInterceptorConfigurerAdapter extends WebMvcConfigurerAdapter {
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
