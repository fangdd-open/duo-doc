package com.fangdd.tp.service.impl.oauth2;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.tp.core.exceptions.Http401Exception;
import com.fangdd.tp.core.exceptions.TpServerException;
import com.fangdd.tp.dao.OAuth2ConfDao;
import com.fangdd.tp.dto.oauth.OAuth2ServiceInfo;
import com.fangdd.tp.dto.oauth.OAuth2TokenReq;
import com.fangdd.tp.dto.oauth.OAuth2UserInfo;
import com.fangdd.tp.dto.oauth.TokenInfo;
import com.fangdd.tp.dto.request.WebRestInvokeData;
import com.fangdd.tp.doclet.pojo.entity.RequestParam;
import com.fangdd.tp.dto.response.InvokeResultDto;
import com.fangdd.tp.entity.OAuth2Conf;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.service.InvokeService;
import com.fangdd.tp.service.OAuth2Service;
import com.fangdd.tp.service.UserService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ycoe
 * @date 18/11/29
 */
public abstract class BaseOAuth2Service implements OAuth2Service {
    private static final Logger logger = LoggerFactory.getLogger(BaseOAuth2Service.class);
    private static final Map<String, OAuth2ServiceInfo> AUTH_SERVICE_INFO_MAP = Maps.newConcurrentMap();
    private static final String INVALID_REQUEST = "非法请求！";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";

    private static final String SERVER = "server";

    @Autowired
    private OAuth2ConfDao oAuth2ConfDao;

    @Autowired
    private InvokeService invokeService;

    @Autowired
    private UserService userService;

    /**
     * 获取登录链接
     *
     * @param site      网站代码
     * @param returnUrl 登录成功后跳转的链接
     * @return
     */
    @Override
    public String getLoginUrl(String site, String returnUrl) {
        OAuth2ServiceInfo serverInfo = getOAuth2ServiceInfo(site);
        return getOAuth2LoginUrl(serverInfo, returnUrl);
    }

    @Override
    public User login(OAuth2TokenReq request) {
        String state = request.getState();
        if (Strings.isNullOrEmpty(state)) {
            throw new TpServerException(500, INVALID_REQUEST);
        }
        if (!checkState(state)) {
            throw new TpServerException(501, INVALID_REQUEST);
        }

        String code = request.getToken();
        if (Strings.isNullOrEmpty(code)) {
            throw new TpServerException(502, INVALID_REQUEST);
        }

        OAuth2ServiceInfo serviceInfo = getOAuth2ServiceInfo(request.getSite());
        WebRestInvokeData invokeRequest = new WebRestInvokeData();
        invokeRequest.setUrl(serviceInfo.getAccessTokenApi());
        invokeRequest.setMethod(HttpMethod.GET.name());

        List<RequestParam> params = getAccessTokenUrlParams(serviceInfo, request);
        if (!CollectionUtils.isEmpty(params)) {
            invokeRequest.setParams(params);
        }
        InvokeResultDto result = invokeService.invoke(null, invokeRequest);

        String body = result.getResponseBody();
        if (Strings.isNullOrEmpty(body)) {
            throw new Http401Exception();
        }
        logger.info("tokenInfo:{}", body);

        TokenInfo tokenInfo = JSONObject.parseObject(body, TokenInfo.class);
        tokenInfo.setSite(request.getSite());

        User user = userService.getByAccessToken(getOAuth2ServiceCode(), tokenInfo);
        if (user != null) {
            //用户已经存在，直接登录
            return userService.login(user, getOAuth2ServiceCode(), tokenInfo);
        }

        //通过accessToken获取用户信息
        WebRestInvokeData userInvokeRequest = new WebRestInvokeData();
        userInvokeRequest.setUrl(serviceInfo.getUserInfoApi());
        userInvokeRequest.setMethod(HttpMethod.GET.name());

        ArrayList<RequestParam> userInfoReqParams = Lists.newArrayList();
        userInfoReqParams.add(getRequestParam("access_token", tokenInfo.getAccessToken()));
        userInfoReqParams.add(getRequestParam("scope", "USERNAME"));
        userInvokeRequest.setParams(userInfoReqParams);

        List<RequestParam> userInvokeHeaders = Lists.newArrayList();
        userInvokeHeaders.add(getRequestParam("Authorization: Bearer ", tokenInfo.getAccessToken()));

        InvokeResultDto userResp = invokeService.invoke(null, userInvokeRequest);
        if (userResp == null || Strings.isNullOrEmpty(userResp.getResponseBody())) {
            //获取用户信息失败
            throw new TpServerException(505, "获取用户基本信息失败！");
        }

        logger.info("获取到的用户信息：{}", userResp.getResponseBody());
        OAuth2UserInfo userInfo = getUserInfo(userResp.getResponseBody());

        //需要注册
        return userService.regist(getOAuth2ServiceCode(), tokenInfo, userInfo);
    }

    /**
     * 获取用户基本信息
     * @param responseBody 响应体
     * @return
     */
    protected abstract OAuth2UserInfo getUserInfo(String responseBody);

    /**
     * 获取accessTokenApi请求的URL参数
     * @param serviceInfo
     * @param request
     * @return
     */
    protected abstract List<RequestParam> getAccessTokenUrlParams(OAuth2ServiceInfo serviceInfo, OAuth2TokenReq request);

    /**
     * 获取OAuth2登录URL
     * @param serviceInfo
     * @param returnUrl
     * @return
     */
    protected abstract String getOAuth2LoginUrl(OAuth2ServiceInfo serviceInfo, String returnUrl);

    /**
     * OAuth2登录URL（不带参数）
     * @return
     */
    protected abstract String getLoginUrlApi();

    /**
     * 获取
     * @return
     */
    protected abstract String getAccessTokenApi();

    protected abstract String getUserInfoApi();

    String getState() {
        StringBuilder sb = new StringBuilder(String.valueOf(System.currentTimeMillis()));
        sb.reverse();
        return sb.substring(0, 6);
    }

    boolean checkState(String state) {
        long now = System.currentTimeMillis();
        long t = now % 1000000;

        StringBuilder sb = new StringBuilder(state);
        sb.reverse();

        long span = t - Long.parseLong(sb.toString()); //时差
        return span < 1000 * 60 * 5; //超过5分钟，即为失效
    }

    RequestParam getRequestParam(String key, String value) {
        RequestParam param = new RequestParam();
        param.setKey(key);
        param.setValue(value);
        return param;
    }

    private OAuth2ServiceInfo getOAuth2ServiceInfo(String site) {
        String key = site + "-" + getOAuth2ServiceCode();
        OAuth2ServiceInfo info = AUTH_SERVICE_INFO_MAP.get(key);
        if (info != null) {
            return info;
        }

        OAuth2Conf conf = oAuth2ConfDao.getEntityById(key);
        if (conf == null) {
            throw new TpServerException(500, "未配置" + getOAuth2ServiceCode() + ".OAuth2");
        }
        Map<String, String> confData = conf.getConf();
        String oAuthServer = confData.get(SERVER);

        info = new OAuth2ServiceInfo();
        info.setServerName(key);
        info.setLoginUrlApi(oAuthServer + getLoginUrlApi());
        info.setAccessTokenApi(oAuthServer + getAccessTokenApi());
        info.setUserInfoApi(oAuthServer + getUserInfoApi());
        info.setClientId(confData.get(CLIENT_ID));
        info.setClientSecret(confData.get(CLIENT_SECRET));
        AUTH_SERVICE_INFO_MAP.put(key, info);
        return info;
    }
}
