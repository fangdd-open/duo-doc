package com.fangdd.tp.controller.api;

import com.fangdd.tp.service.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auth ycoe
 * @date 18/10/15
 */
@RestController
@RequestMapping("/api/dubbo")
public class DubboApiController {
    @Autowired
    private DubboService dubboService;

    /**
     * 调用dubbo接口
     *
     * @param env     环境：dev=开发环境 test=测试环境 build=预发布环境 prod=生产环境
     * @param service Dubbo服务类（全名）
     * @param method  方法
     * @param body    请求参数
     * @return
     */
    @PostMapping("/{env:dev|test|build|prod}/{service}/{method}/")
    public Object invoke(
            @PathVariable String env,
            @PathVariable String service,
            @PathVariable String method,
            @RequestBody String body
    ) {
        return dubboService.invoke(env, service, method, body);
    }
}
