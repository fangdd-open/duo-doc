package com.fangdd.doclet.test.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.fangdd.doclet.test.dto.User;
import com.fangdd.doclet.test.service.UserService;

/**
 * @auth ycoe
 * @date 19/1/4
 */
@Service(version="1.0.0")
public class UserServiceImpl implements UserService {
    /**
     * 通过ID获取用户基本信息
     *
     * @param id 用户ID
     * @return
     */
    public User get(int id) {
        return null;
    }
}
