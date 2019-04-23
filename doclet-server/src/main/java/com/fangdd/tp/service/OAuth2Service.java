package com.fangdd.tp.service;

import com.fangdd.tp.dto.oauth.OAuth2TokenReq;
import com.fangdd.tp.entity.User;

/**
 * @author ycoe
 * @date 18/11/28
 */
public interface OAuth2Service {
    /**
     * 获取OAuth2服务名称，比如fdd / wx
     *
     * @return
     */
    String getOAuth2ServiceCode();

    /**
     * 获取登录链接
     *
     * @param code      组织代码
     * @param returnUrl 登录成功后跳转的链接
     * @return
     */
    String getLoginUrl(String code, String returnUrl);

    /**
     * 通过三方OAuth2服务返回的token登录
     *
     * @param tokenReq OAuth2TokenReq
     * @return
     */
    User login(OAuth2TokenReq tokenReq);

    /**
     * 退出登录
     *
     * @param user 当前用户
     */
    void logout(User user);
}
