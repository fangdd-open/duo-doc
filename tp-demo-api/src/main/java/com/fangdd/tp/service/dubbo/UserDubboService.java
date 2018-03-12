package com.fangdd.tp.service.dubbo;

import com.fangdd.tp.dto.Admin;
import com.fangdd.tp.dto.User;

/**
 * 用户服务
 * @auth ycoe
 * @date 18/3/2
 */
public interface UserDubboService {
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
     * 通过用户ID获取用户信息
     * 如果用户ID为空时，将返回null
     *
     * @param id     用户ID
     * @return 用户基本信息
     */
    User<Admin> get(Long id);
}
