package com.fangdd.tp.core.config.http;

import com.fangdd.tp.core.annotation.Account;
import com.fangdd.tp.core.exceptions.DuoServerException;
import com.fangdd.tp.core.exceptions.Http401Exception;
import com.fangdd.tp.core.exceptions.Http403Exception;
import com.fangdd.tp.dto.UserContent;
import com.fangdd.tp.entity.Site;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.enums.RoleEnum;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.SiteService;
import com.fangdd.tp.service.UserService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Account 注解的验证
 *
 * @author xuwenzhen
 */
public class AccountInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AccountInterceptor.class);
    private static final Map<String, Site> SITE_MAP = new ConcurrentHashMap<>();
    private static final String AUTH_TOKEN = "auth-token";
    private static final String DOMAIN = "domain";

    private static final String DOMAIN2 = "Host";
    private static final String STR_SITE_INITED = "siteInited";
    /**
     * 是否已初始化过站点
     */
    private boolean initSite = false;

    @Autowired
    private UserService userService;

    @Autowired
    private SiteService siteService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserContent userContent = new UserContent();

        String host = request.getHeader(DOMAIN);
        if (Strings.isNullOrEmpty(host)) {
            host = request.getHeader(DOMAIN2);
        }
        Site site = getSite(host);
        userContent.setSite(site);

        User user = null;
        String authToken = request.getHeader(AUTH_TOKEN);
        if (!Strings.isNullOrEmpty(authToken)) {
            user = userService.getByToken(authToken);
            if (user != null) {
                user.getAuths().forEach(authInfo -> {
                    authInfo.setAccessToken(null);
                    authInfo.setRefreshToken(null);
                    authInfo.setGid(null);
                });
                user.setCurrentSite(site);
                userContent.setUser(user);
            }
        }
        UserContextHelper.setUserContext(userContent);


        Method method = ((HandlerMethod) handler).getMethod();
        Account account = method.getAnnotation(Account.class);
        if (account == null) {
            //未限制
            return true;
        }
        if (!checkAccount(user, account)) {
            throw new Http403Exception();
        }
        return true;
    }

    private Site getSite(String domain) {
        Site site = SITE_MAP.computeIfAbsent(domain, this::getByDomainOrInit);
        if (site == null) {
            throw new DuoServerException(404, "无效域名:" + domain);
        }

        return site;
    }

    private Site getByDomainOrInit(String domain) {
        Site site = siteService.getByHost(domain);
        if (site != null) {
            return site;
        }
        if (initSite) {
            //如果已经初始化过
            return null;
        }

        //检查站点是否有初始化过
        if (siteService.isEmpty()) {
            //如果没有初始化站点，则尝试初始化
            site = siteService.init(domain);
        }
        initSite = true;
        return site;
    }

    private boolean checkAccount(User user, Account account) {
        if (user == null) {
            throw new Http401Exception();
        }

        if (RoleEnum.ADMIN.getRole() == user.getRole()) {
            return true;
        }

        return account.value().getRole() <= user.getRole();
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        UserContextHelper.setUserContext(null);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }
}
