package com.fangdd.tp.service;

import com.fangdd.tp.dto.Admin;
import com.fangdd.tp.dto.User;
import com.fangdd.tp.dto.UserFilter;

import java.util.List;
import java.util.Map;

/**
 * 用户基本信息接口
 * 提供用户信息获取、查询、修改、注册等功能
 *
 * @chapter 用户接口
 * @rank 10
 * @auth ycoe
 * @date 18/1/5
 */
public interface UserService {
    /**
     * 通过用户ID获取用户信息
     * 如果用户ID为空时，将返回null
     *
     * @param id     用户ID
     * @param client 客户端信息
     * @return 用户基本信息
     */
    User<Admin> get(Long id, String client);

    /**
     * 筛选用户
     *
     * @return 用户列表
     */
    User<Admin>[] find();

    /**
     * 用户搜索
     * @param filter 搜索条件
     * @return 返回命中的用户列表，未命中返回null
     */
    List<User<Admin>> search(UserFilter filter);

    /**
     * 筛选用户
     *
     * @return 用户Map，key是用户ID
     */
    Map<Integer, User<Admin>> map();
}
