package com.fangdd.tp.service.impl;

import com.fangdd.tp.core.exceptions.DuoServerException;
import com.fangdd.tp.dao.UserDao;
import com.fangdd.tp.dto.oauth.OAuth2UserInfo;
import com.fangdd.tp.dto.oauth.TokenInfo;
import com.fangdd.tp.dto.request.PasswordLoginReq;
import com.fangdd.tp.dto.response.SimpleUserDto;
import com.fangdd.tp.entity.AuthInfo;
import com.fangdd.tp.entity.Site;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.enums.RoleEnum;
import com.fangdd.tp.helper.MD5Utils;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.UserService;
import com.fangdd.traffic.common.mongo.utils.UUIDUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author xuwenzhen
 * @date 18/11/28
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    /**
     * 一个月过期
     */
    private static final Long TOKEN_EXPIRE_IN = 1000L * 60 * 60 * 24 * 30;

    private static final String ID = "_id";

    private static final String DOCS_OWNER = "docsOwner";
    private static final String AUTHS = "auths";
    private static final String TOKEN_EXPIRED = "tokenExpired";
    private static final String TOKEN = "token";
    private static final String AUTHS_CODE = "auths.code";
    private static final String AUTHS_GID = "auths.gid";
    private static final String NAME = "name";
    private static final String MOBILE = "mobile";
    private static final String EMAIL = "email";
    private static final String STR_PWD = "pwd";
    private static final String STR_ADMIN = "admin";
    private static final String Str_GUEST = "guest";

    @Autowired
    private UserDao userDao;

    @Override
    public User getByToken(String authToken) {
        Bson filter = Filters.eq(TOKEN, authToken);
        User user = userDao.getEntity(filter);
        if (user == null) {
            // token失效
            return null;
        }
        if (user.getStatus() == null || user.getStatus() < 0) {
            // 用户已被禁用
            logout(user);
            return null;
        }
        if (user.getTokenExpired() != null && user.getTokenExpired() < System.currentTimeMillis()) {
            // token已过期
            logout(user);
            return null;
        }
        return user;
    }

    private void logout(User user) {
        userDao.updateOne(Filters.eq(ID, user.getId()), Updates.combine(
                Updates.unset(TOKEN)
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
                Filters.eq(AUTHS_CODE, code),
                Filters.eq(AUTHS_GID, tokenInfo.getUserId())
        );
        return userDao.getEntity(filter);
    }

    @Override
    public User registy(String code, TokenInfo tokenInfo, OAuth2UserInfo userInfo) {
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
            throw new DuoServerException(404, "无法找到此用户！");
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
            throw new DuoServerException(404, "无法找到此用户！");
        }
        List<String> docList = user.getDocsOwner();
        if (!CollectionUtils.isEmpty(docList)) {
            docList.remove(docId);
            userDao.updateOne(idFilter, Updates.set(DOCS_OWNER, docList));
        }
        return true;
    }

    @Override
    public Map<Long, User> getByIds(Set<Long> userIds) {
        Map<Long, User> userMap = Maps.newHashMap();
        List<User> userList = userDao
                .find(Filters.in(ID, userIds))
                .projection(Projections.exclude(AUTHS, TOKEN_EXPIRED, TOKEN))
                .into(Lists.newArrayList());
        if (!CollectionUtils.isEmpty(userList)) {
            userList.forEach(user -> userMap.put(user.getId(), user));
        }
        return userMap;
    }

    @Override
    public List<SimpleUserDto> getOwners(String docId) {
        return userDao
                .getDocumentMongoCollection(SimpleUserDto.class)
                .find(Filters.eq(DOCS_OWNER, docId))
                .projection(Projections.include(NAME, MOBILE, EMAIL))
                .into(Lists.newArrayList());
    }

    /**
     * 初始化站点用户
     * 会初始化两个用户：admin / guest
     *
     * @param site 站点
     */
    @Override
    public void init(Site site) {
        String siteId = site.getId();
        String salt = site.getSalt();
        String publishKey = site.getPublicKey();
        AuthInfo pwdAuth = new AuthInfo();
        pwdAuth.setCode(STR_PWD);
        pwdAuth.setGid(STR_ADMIN);
        String publishPwd = MD5Utils.md5(publishKey + STR_ADMIN);
        pwdAuth.setPassword(MD5Utils.md5(publishPwd + salt));

        User admin = new User();
        admin.setName("管理员");
        admin.setSites(Lists.newArrayList(siteId));
        admin.setRole(RoleEnum.ADMIN.getRole());
        admin.setStatus(1);
        admin.setCreateTime(System.currentTimeMillis());
        admin.setAuths(Lists.newArrayList(pwdAuth));
        userDao.insertOne(admin);

        AuthInfo guestPwdAuth = new AuthInfo();
        guestPwdAuth.setCode(STR_PWD);
        guestPwdAuth.setGid(Str_GUEST);
        String guestPublishPwd = MD5Utils.md5(publishKey + Str_GUEST);
        guestPwdAuth.setPassword(MD5Utils.md5(guestPublishPwd + salt));
        User guest = new User();
        guest.setName("游客");
        guest.setSites(Lists.newArrayList(siteId));
        guest.setRole(RoleEnum.USER.getRole());
        guest.setStatus(1);
        guest.setCreateTime(System.currentTimeMillis());
        guest.setAuths(Lists.newArrayList(guestPwdAuth));
        userDao.insertOne(guest);
        logger.info("初始化网站普通用户:guest");
    }

    /**
     * 使用账号密码登录
     *
     * @param request 登录请求
     * @return 登录成功的用户，登录失败会抛异常
     */
    @Override
    public User loginByPassword(PasswordLoginReq request) {
        String userName = request.getUserName();
        if (StringUtils.isEmpty(userName)) {
            throw new DuoServerException(500, "用户名不能为空！");
        }
        String password = request.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw new DuoServerException(500, "密码不能为空！");
        }
        User user = userDao.getEntity(
                Filters.and(
                        Filters.eq(AUTHS_GID, userName),
                        Filters.eq(AUTHS_CODE, STR_PWD)
                )
        );
        if (user == null) {
            throw new DuoServerException(500, "用户不存在！");
        }
        Optional<AuthInfo> authOptional = user.getAuths().stream().filter(item -> STR_PWD.equals(item.getCode())).findFirst();
        if (!authOptional.isPresent()) {
            throw new DuoServerException(500, "数据异常！");
        }

        AuthInfo authInfo = authOptional.get();

        Site site = UserContextHelper.getSite();
        String salt = site.getSalt();
        password = MD5Utils.md5(password + salt);
        if (password == null || !password.equals(authInfo.getPassword())) {
            throw new DuoServerException(500, "密码错误！");
        }

        long now = System.currentTimeMillis();
        user.setTokenExpired(now + TOKEN_EXPIRE_IN);
        user.setLoginCode(STR_PWD);
        user.setToken(UUIDUtils.generateUUID());
        user.setLastLoginTime(now);
        userDao.updateEntity(user);
        return user;
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
