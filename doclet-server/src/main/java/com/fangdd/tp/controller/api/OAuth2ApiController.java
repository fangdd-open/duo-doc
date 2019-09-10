package com.fangdd.tp.controller.api;

import com.fangdd.tp.core.exceptions.TpServerException;
import com.fangdd.tp.dto.BaseResponse;
import com.fangdd.tp.dto.UserContent;
import com.fangdd.tp.dto.oauth.OAuth2TokenReq;
import com.fangdd.tp.entity.Site;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.OAuth2Service;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author xuwenzhen
 * @date 18/11/28
 * @disable
 */
@Controller
@RequestMapping("/api/oauth2")
public class OAuth2ApiController {
    @Autowired
    private List<OAuth2Service> oAuth2ServiceList;

    /**
     * 获取OAuth2登录链接
     *
     * @param authServiceCode 授权服务代码
     * @param returnUrl       登录成功后返回的链接
     * @return
     */
    @GetMapping("/{authServiceCode}")
    public RedirectView getLoginUrl(
            @PathVariable String authServiceCode,
            @RequestParam String returnUrl
    ) {
        OAuth2Service oAuth2Service = getOAuth2Service(authServiceCode);
        if (oAuth2Service == null) {
            throw new TpServerException(404, "no oauth support");
        }

        Site site = UserContextHelper.getSite();
        String url = oAuth2Service.getLoginUrl(site.getId(), returnUrl);
        return new RedirectView(url);
    }

    /**
     * 注销登录
     *
     * @param authServiceCode 授权平台代码
     * @return
     */
    @DeleteMapping("/{authServiceCode}/logout")
    public
    @ResponseBody
    BaseResponse<Boolean> logout(@PathVariable String authServiceCode) {
        OAuth2Service oAuth2Service = getOAuth2Service(authServiceCode);
        if (oAuth2Service == null) {
            return BaseResponse.error(500, "非法请求!");
        }
        UserContent content = UserContextHelper.getUserContext();
        oAuth2Service.logout(content.getUser());
        return BaseResponse.success(true);
    }

    @RequestMapping("/{authServiceCode}/callback")
    public RedirectView callback(
            @PathVariable String authServiceCode,
            HttpServletRequest req,
            HttpServletResponse response,
            RedirectAttributes attrs
    ) {
        OAuth2Service oAuth2Service = getOAuth2Service(authServiceCode);
        if (oAuth2Service == null) {
            attrs.addAttribute("error", "非法请求");
            return logRedirect(attrs, "非法请求");
        }

        String error = req.getParameter("error");
        if (!Strings.isNullOrEmpty(error)) {
            return logRedirect(attrs, req.getParameter("error_description"));
        }

        String state = req.getParameter("state");
        String code = req.getParameter("code");
        UserContent content = UserContextHelper.getUserContext();
        OAuth2TokenReq request = new OAuth2TokenReq();
        request.setSite(content.getSite().getId());
        request.setToken(code);
        request.setState(state);
        request.setReturnUrl("/user/oauth");

        User user = oAuth2Service.login(request);

        String domain = req.getHeader("Host");
        Cookie cookie = new Cookie("tp_doc_token", user.getToken());
        cookie.setDomain(domain);
        cookie.setPath("/");
//        cookie.setMaxAge(300);
        response.addCookie(cookie);

        return new RedirectView("/user/oauth/" + user.getToken());
    }

    private RedirectView logRedirect(RedirectAttributes attrs, String error) {
        if (!Strings.isNullOrEmpty(error)) {
            attrs.addAttribute("error", error);
        }
        return new RedirectView("/user/oauth");
    }

    /**
     * 登录
     *
     * @param tokenReq
     * @return
     */
    @PostMapping
    public BaseResponse<User> doLogin(@RequestBody OAuth2TokenReq tokenReq) {
        String site = tokenReq.getSite();
        OAuth2Service oAuth2Service = getOAuth2Service(site);
        if (oAuth2Service == null) {
            return BaseResponse.error(404, "当前账户不支持OAuth2登录！");
        }
        return BaseResponse.success(oAuth2Service.login(tokenReq));
    }

    private OAuth2Service getOAuth2Service(String code) {
        if (Strings.isNullOrEmpty(code) || CollectionUtils.isEmpty(oAuth2ServiceList)) {
            return null;
        }
        for (OAuth2Service oAuth2Service : oAuth2ServiceList) {
            if (oAuth2Service.getOAuth2ServiceCode().equals(code)) {
                return oAuth2Service;
            }
        }
        return null;
    }
}
