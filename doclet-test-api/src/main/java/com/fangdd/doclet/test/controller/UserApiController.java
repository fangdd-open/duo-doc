package com.fangdd.doclet.test.controller;

import com.fangdd.cp.dto.response.BaseResponse;
import com.fangdd.cp.dto.response.PagedListDto;
import com.fangdd.doclet.test.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuwenzhen
 * @chapter 用户接口
 * @section 用户信息
 * @date 2019/8/15
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController {
    @GetMapping("/list")
    public BaseResponse<PagedListDto<User>> list() {
        return BaseResponse.success();
    }
}
