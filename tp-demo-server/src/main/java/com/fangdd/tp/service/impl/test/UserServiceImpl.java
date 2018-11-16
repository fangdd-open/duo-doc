package com.fangdd.tp.service.impl.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.fangdd.tp.dto.Admin;
import com.fangdd.tp.dto.TestUser;
import com.fangdd.tp.dto.UserFilter;
import com.fangdd.tp.service.UserServiceTest;

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
public class UserServiceImpl implements UserServiceTest {

    @Reference(version = "1.0.0")
    private UserServiceTest userService;

    /**
     * 通过用户ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户基本信息
     */
    public TestUser<Admin> get(Long id, String client) {
        return null;
    }

    @Override
    public TestUser<Admin>[] find() {
        return null;
    }

    @Override
    public Map<Integer, TestUser<Admin>> map() {
        return null;
    }

    @Override
    public List<TestUser<Admin>> search(UserFilter filter) {
        return null;
    }
}
