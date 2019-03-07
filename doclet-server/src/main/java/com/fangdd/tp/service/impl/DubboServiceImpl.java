package com.fangdd.tp.service.impl;

import com.fangdd.tp.service.DubboService;
import org.springframework.stereotype.Service;

/**
 * @author ycoe
 * @date 18/10/15
 */
@Service
public class DubboServiceImpl implements DubboService {
    /**
     * 调用dubbo接口
     *
     * @param env     环境：dev=开发环境 test=测试环境 build=预发布环境 prod=生产环境
     * @param service Dubbo服务类（全名）
     * @param method  方法
     * @param body    请求参数
     * @return
     */
    @Override
    public Object invoke(String env, String service, String method, String body) {

        return null;
    }
}
