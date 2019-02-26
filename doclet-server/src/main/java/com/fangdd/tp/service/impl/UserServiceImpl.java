package com.fangdd.tp.service.impl;

import com.fangdd.tp.core.exceptions.TpServerException;
import com.fangdd.tp.dao.UserDao;
import com.fangdd.tp.dto.oauth.OAuth2UserInfo;
import com.fangdd.tp.dto.oauth.TokenInfo;
import com.fangdd.tp.entity.AuthInfo;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.enums.RoleEnum;
import com.fangdd.tp.service.UserService;
import com.fangdd.traffic.common.mongo.utils.UUIDUtils;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/11/28
 */
@Service
public class UserServiceImpl implements UserService {
    //一个月过期
    private static final Long TOKEN_EXPIRE_IN = 1000L * 60 * 60 * 24 * 30;

    private static final String ID = "_id";

    private static final String DOCS_OWNER = "docsOwner";

    @Autowired
    private UserDao userDao;

    @Override
    public User getByToken(String authToken) {
        Bson filter = Filters.eq("token", authToken);
        User user = userDao.getEntity(filter);
        if (user == null) {
            // token失效
            return null;
//            throw new Http401Exception();
        }
        if (user.getStatus() == null || user.getStatus() < 0) {
            // 用户已被禁用
            logout(user);
            return null;
//            throw new TpServerException(404, "账户已被禁用！");
        }
        if (user.getTokenExpired() != null && user.getTokenExpired() < System.currentTimeMillis()) {
            // token已过期
            logout(user);
            return null;
//            throw new Http401Exception();
        }
        return user;
    }

    private void logout(User user) {
        userDao.updateOne(Filters.eq(ID, user.getId()), Updates.combine(
                Updates.unset("token")
        ));
    }

    @Override
    public User get(Long id) {
        return userDao.getEntityById(id);
    }

    @Override
    public User login(User user, String code, TokenInfo tokenInfo) {
        long now = System.currentTimeMillis();
        user.setTokenExpired(now + TOKEN_EXPIRE_IN);
        user.setLoginCode(code);
        user.setToken(UUIDUtils.generateUUID());
        user.setLastLoginTime(now);
        List<AuthInfo> auths = user.getAuths();
        boolean exists = false;
        for (AuthInfo authInfo : auths) {
            if (authInfo.getCode().equals(code)) {
                exists = true;
                authInfo.setAccessToken(tokenInfo.getAccessToken());
                authInfo.setRefreshToken(tokenInfo.getRefreshToken());
                break;
            }
        }
        if (!exists) {
            AuthInfo authInfo = newAuthInfo(code, tokenInfo);
            auths.add(authInfo);
        }
        userDao.updateEntity(user);
        return user;
    }

    @Override
    public User getByAccessToken(String code, TokenInfo tokenInfo) {
        Bson filter = Filters.and(
                Filters.eq("auths.code", code),
                Filters.eq("auths.gid", tokenInfo.getUserId())
        );
        return userDao.getEntity(filter);
    }

    @Override
    public User regist(String code, TokenInfo tokenInfo, OAuth2UserInfo userInfo) {
        User user = new User();
        user.setName(userInfo.getName());
        user.setSites(Lists.newArrayList(tokenInfo.getSite()));
        user.setRole(RoleEnum.USER.getRole());
        user.setToken(UUIDUtils.generateUUID());
        long now = System.currentTimeMillis();
        user.setTokenExpired(now + TOKEN_EXPIRE_IN);
        user.setStatus(1);
        user.setLoginCode(code);
        user.setLastLoginTime(now);
        user.setCreateTime(now);
        List<AuthInfo> auths = Lists.newArrayList();
        AuthInfo authInfo = newAuthInfo(code, tokenInfo);
        auths.add(authInfo);
        user.setAuths(auths);
        userDao.insertOne(user);
        return user;
    }

    @Override
    public Boolean addDocOwner(String docId, Long userId) {
        Bson idFilter = Filters.eq(ID, userId);
        User user = userDao.getEntity(idFilter, Projections.include(DOCS_OWNER));
        if (user == null) {
            throw new TpServerException(404, "无法找到此用户！");
        }
        List<String> docList = user.getDocsOwner();
        if (docList == null || !docList.contains(docId)) {
            userDao.updateOne(idFilter, Updates.addToSet(DOCS_OWNER, docId));
        }
        return true;
    }

    @Override
    public Boolean removeDocOwner(String docId, Long userId) {
        Bson idFilter = Filters.eq(ID, userId);
        User user = userDao.getEntity(idFilter, Projections.include(DOCS_OWNER));
        if (user == null) {
            throw new TpServerException(404, "无法找到此用户！");
        }
        List<String> docList = user.getDocsOwner();
        if (!CollectionUtils.isEmpty(docList)) {
            docList.remove(docId);
            userDao.updateOne(idFilter, Updates.set(DOCS_OWNER, docList));
        }
        return true;
    }

    private AuthInfo newAuthInfo(String code, TokenInfo tokenInfo) {
        AuthInfo authInfo = new AuthInfo();
        authInfo.setCode(code);
        authInfo.setGid(tokenInfo.getUserId());
        authInfo.setAccessToken(tokenInfo.getAccessToken());
        authInfo.setRefreshToken(tokenInfo.getRefreshToken());
        return authInfo;
    }
}
