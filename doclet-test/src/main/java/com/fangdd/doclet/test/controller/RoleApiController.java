package com.fangdd.doclet.test.controller;

import com.fangdd.doclet.test.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @chapter 角色接口
 * @c1 20
 * @author xuwenzhen
 * @date 2019/3/4
 */
//@RestController
//@RequestMapping("/api/role")
public class RoleApiController {
    /**
     * 查询用户角色信息
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/get")
    public User getRole(Long userId) {
        return null;
    }

    /**
     *
     * @disable
     * @param userId
     * @return
     */
//    @GetMapping("/user2")
//    public User getDetails(Long userId) {
//        return null;
//    }
}
