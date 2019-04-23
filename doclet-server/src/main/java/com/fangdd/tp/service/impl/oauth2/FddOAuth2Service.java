package com.fangdd.tp.service.impl.oauth2;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.tp.dto.oauth.OAuth2ServiceInfo;
import com.fangdd.tp.dto.oauth.OAuth2TokenReq;
import com.fangdd.tp.dto.oauth.OAuth2UserInfo;
import com.fangdd.tp.doclet.pojo.entity.RequestParam;
import com.fangdd.tp.entity.AuthInfo;
import com.fangdd.tp.entity.User;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 房多多OAuth2服务
 * 文档：http://confluence.fangdd.cn/pages/viewpage.action?pageId=144736412
 *
 * @author ycoe
 * @date 18/11/28
 */
@Service
public class FddOAuth2Service extends BaseOAuth2Service {
    private static final String LOGIN_API = "/oauth2/authorize";
    private static final String USER_INFO_API = "/oauth2/token";
    private static final String EMPLOYEE_API = "/api/employee";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REDIRECT_URI = "redirect_uri";
    private static final String CODE = "code";
    private static final String GRANT_TYPE = "grant_type";
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String FDD_UC = "fdd_uc";
    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String EMAIL = "email";
    private static final String MOBILE = "mobile";

    /**
     * 获取组织代码，比如fdd
     *
     * @return
     */
    @Override
    public String getOAuth2ServiceCode() {
        return FDD_UC;
    }

    @Override
    public void logout(User user) {
        List<AuthInfo> auths = user.getAuths();
        Optional<AuthInfo> fddAuth = auths.stream().filter(auth -> FDD_UC.equals(auth.getCode())).findFirst();
        if (!fddAuth.isPresent()) {
            return;
        }
        AuthInfo authInfo = fddAuth.get();
//        authInfo.getAccessToken()
    }

    /**
     * //{"id":1898580,"name":"徐文振","gender":1,"email":"xuwenzhen@fangdd.com","mobile":"15014110313","locality":"研发-深圳","badge":"P001765","status":1,"passportId":100001815,"statusName":"在职","genderName":"男"}
     * @param responseBody 响应体
     * @return
     */
    @Override
    protected OAuth2UserInfo getUserInfo(String responseBody) {
        OAuth2UserInfo userInfo = new OAuth2UserInfo();
        JSONObject data = JSONObject.parseObject(responseBody);
        userInfo.setId(data.getString(ID));
        userInfo.setName(data.getString(NAME));
        userInfo.setEmail(data.getString(EMAIL));
        userInfo.setMobile(data.getString(MOBILE));
        return userInfo;
    }

    @Override
    protected List<RequestParam> getAccessTokenUrlParams(OAuth2ServiceInfo serviceInfo, OAuth2TokenReq request) {
        List<RequestParam> params = Lists.newArrayList();
        params.add(getRequestParam(CLIENT_ID, serviceInfo.getClientId()));
        params.add(getRequestParam(CLIENT_SECRET, serviceInfo.getClientSecret()));
        params.add(getRequestParam(REDIRECT_URI, request.getReturnUrl()));
        params.add(getRequestParam(CODE, request.getToken()));
        params.add(getRequestParam(GRANT_TYPE, AUTHORIZATION_CODE));
        return params;
    }

    @Override
    protected String getOAuth2LoginUrl(OAuth2ServiceInfo serviceInfo, String redirectUri) {
        return serviceInfo.getLoginUrlApi() + "?" + CLIENT_ID + "=" +
                serviceInfo.getClientId() +
                "&response_type=code&redirect_uri=" +
                redirectUri +
                "&scope=EMPLOYEE&state=" +
                getState();
    }

    @Override
    protected String getLoginUrlApi() {
        return LOGIN_API;
    }

    @Override
    protected String getAccessTokenApi() {
        return USER_INFO_API;
    }

    @Override
    protected String getUserInfoApi() {
        return EMPLOYEE_API;
    }
}
