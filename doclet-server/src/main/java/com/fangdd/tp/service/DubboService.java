package com.fangdd.tp.service;

/**
 * @auth ycoe
 * @date 18/10/15
 */
public interface DubboService {
    /**
     * 调用dubbo接口
     * @param env 环境：dev=开发环境 test=测试环境 build=预发布环境 prod=生产环境
     * @param service Dubbo服务类（全名）
     * @param method 方法
     * @param body 请求参数
     * @return
     */
    Object invoke(String env, String service, String method, String body);
}
