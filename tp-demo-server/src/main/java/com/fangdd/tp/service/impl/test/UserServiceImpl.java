package com.fangdd.tp.service.impl.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.fangdd.tp.dto.Admin;
import com.fangdd.tp.dto.User;
import com.fangdd.tp.dto.UserFilter;
import com.fangdd.tp.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * 用户接口
 *
 * @auth ycoe
 * @date 18/1/5
 * @rank 12
 */
@Service(version = "1.0.0")
public class UserServiceImpl implements UserService {

    @Reference(version = "1.0.0")
    private UserService userService;

    /**
     * 通过用户ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户基本信息
     */
    public User<Admin> get(Long id, String client) {
        return null;
    }

    @Override
    public User<Admin>[] find() {
        return null;
    }

    @Override
    public Map<Integer, User<Admin>> map() {
        return null;
    }

    @Override
    public List<User<Admin>> search(UserFilter filter) {
        return null;
    }
}
