package com.fangdd.tp.controller.api.test;

import com.fangdd.tp.dto.BaseResponse;
import com.fangdd.tp.dto.Seoer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth ycoe
 * @date 18/11/8
 */
@RestController
@RequestMapping("/api/user")
public class UserTestApiController {
    @GetMapping("/{id:\\d+}")
    public BaseResponse<Seoer> getAdmin2(@PathVariable Long id) {

        return BaseResponse.success();
    }
}
