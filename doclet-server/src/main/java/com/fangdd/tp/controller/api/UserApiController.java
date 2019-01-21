package com.fangdd.tp.controller.api;

import com.fangdd.tp.core.annotation.Account;
import com.fangdd.tp.dto.BaseResponse;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.enums.RoleEnum;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @chapter Rest接口
 * @section 用户接口
 * @auth ycoe
 * @date 18/1/18
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    private UserService userService;

    /**
     * 通过ID获取用户基本信息
     *
     * @param id 用户ID
     * @return 用户基本信息
     */
    @Account(RoleEnum.ADMIN)
    @GetMapping(value = "/{id:\\d+}")
    public BaseResponse<User> get(@PathVariable Long id) {
        return BaseResponse.success(userService.get(id));
    }

    /**
     * 通过Token获取用户基本信息
     *
     * @param token 用户token
     * @return 用户基本信息
     */
    @Account(RoleEnum.ADMIN)
    @GetMapping(value = "/token/{token}")
    public BaseResponse<User> getByToken(@PathVariable String token) {
        return BaseResponse.success(userService.getByToken(token));
    }

    /**
     * 获取当前用户的基本信息
     *
     * @return 用户基本信息
     */
    @Account(RoleEnum.USER)
    @GetMapping(value = "/my")
    public BaseResponse<User> getMyInfo() {
        return BaseResponse.success(UserContextHelper.getUserContext().getUser());
    }
}
