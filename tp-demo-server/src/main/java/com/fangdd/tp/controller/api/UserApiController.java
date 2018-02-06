package com.fangdd.tp.controller.api;

import com.fangdd.tp.dto.Admin;
import com.fangdd.tp.dto.BaseResponse;
import com.fangdd.tp.dto.User;
import com.fangdd.tp.dto.UserFilter;
import org.springframework.web.bind.annotation.*;

/**
 * @chapter Rest接口
 * @section 用户接口
 * @auth ycoe
 * @date 18/1/18
 */
@RestController
@RequestMapping("/user")
public class UserApiController {
    /**
     * 通过ID获取用户基本信息
     *
     * @param id     用户ID
     * @param filter 用户筛选
     * @return 用户基本信息
     */
    @GetMapping(value = "/{id:\\d+}")
    public BaseResponse<User<Admin>> get(@PathVariable Long id, UserFilter filter) {

        return BaseResponse.success(null);
    }
}
