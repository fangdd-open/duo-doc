package com.fangdd.tp.service;

import com.fangdd.tp.dto.oauth.OAuth2UserInfo;
import com.fangdd.tp.dto.oauth.TokenInfo;
import com.fangdd.tp.dto.response.SimpleUserDto;
import com.fangdd.tp.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xuwenzhen
 * @date 18/11/28
 */
public interface UserService {
    /**
     * 通过token获取用户信息
     *
     * @param authToken 令牌
     * @return
     */
    User getByToken(String authToken);

    /**
     * 通过ID获取用户基本信息
     *
     * @param id user.id
     * @return
     */
    User get(Long id);

    /**
     * 用户登录
     *
     * @param user
     * @param code
     * @param tokenInfo 三方授权信息
     * @return
     */
    User login(User user, String code, TokenInfo tokenInfo);

    /**
     * 通过用户token获取用户基本信息
     *
     * @param code
     * @param tokenInfo
     * @return
     */
    User getByAccessToken(String code, TokenInfo tokenInfo);

    /**
     * 用户注册
     *
     * @param code
     * @param tokenInfo
     * @param userInfo
     * @return
     */
    User regist(String code, TokenInfo tokenInfo, OAuth2UserInfo userInfo);

    /**
     * 设置某个用户为某个文档的所有者
     *
     * @param docId  文档ID
     * @param userId 用户ID
     * @return
     */
    Boolean addDocOwner(String docId, Long userId);

    /**
     * 移除某个文档的所有者
     *
     * @param docId  文档ID
     * @param userId 用户ID
     * @return
     */
    Boolean removeDocOwner(String docId, Long userId);

    /**
     * 通过用户IDs获取对应的用户信息
     *
     * @param userIds 用户IDs
     * @return
     */
    Map<Long, User> getByIds(Set<Long> userIds);

    /**
     * 查询某个文档的所有者
     * @param docId 文档ID
     * @return 所有者列表
     */
    List<SimpleUserDto> getOwners(String docId);
}
