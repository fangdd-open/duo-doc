package com.fangdd.doclet.test.controller;

import com.fangdd.doclet.test.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xuwenzhen
 * @date 2019/3/4
 */
@RestController
@RequestMapping("/api")
public class UserApiController extends BaseApiController{
    /**
     * 查询用户基本信息
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/user")
    public User getDetail(Long userId) {
        return null;
    }

    /**
     *
     * @disable
     * @param userId
     * @return
     */
    @GetMapping("/user2")
    public User getDetails(Long userId) {
        return null;
    }
}
