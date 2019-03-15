package com.fangdd.doclet.test.controller;

import com.fangdd.doclet.test.dto.User;
import com.fangdd.doclet.test.dto.req.UserSaveReq;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xuwenzhen
 * @chapter 用户接口
 * @c1 30
 * @date 2019/3/4
 */
@RestController
@RequestMapping("/api")
public class UserApiController extends BaseApiController {
    /**
     * 查询用户基本信息
     *
     * @param userId 用户ID
     * @return
     * @disable
     */
    @GetMapping("/user")
    public User getDetail(@Valid Long userId) {
        return null;
    }

    /**
     * 查询用户基本信息2
     *
     * @param userId
     * @disable
     * @return
     */
    @GetMapping("/user2")
    public User getDetails(Long userId) {
        return null;
    }

    /**
     * 保存用户信息
     * @param request 用户信息
     * @return
     */
    @PostMapping("user")
    public User save(@RequestBody UserSaveReq request) {
        return null;
    }
}
