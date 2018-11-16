package com.fangdd.tp.core;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.fangdd.tp.config.DubboConfig;
import com.fangdd.tp.doclet.enums.EnvEnum;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @auth ycoe
 * @date 18/10/15
 */
public class DubboServiceFactory {
    private static final Map<EnvEnum, DubboServiceFactory> DUBBO_SERVICE_FACTORY_MAP = Maps.newConcurrentMap();

    private static DubboConfig dubboConfig;

    private ApplicationConfig application;

    private RegistryConfig registry;

    private DubboServiceFactory(EnvEnum env) {
        if (dubboConfig == null) {
            throw new DocletException("未设置com.fangdd.tp.core.DubboServiceFactory.dubboConfig");
        }
        if (env == null) {
            throw new DocletException("未指定环境！");
        }

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboConfig.getApplicationName());
        //这里配置了dubbo的application信息*(demo只配置了name)*，因此demo没有额外的dubbo.xml配置文件
        RegistryConfig registryConfig = new RegistryConfig();
        switch (env) {
            case DEVELOP:
                registryConfig.setAddress(dubboConfig.getDevelopRegistryAddress());
                break;
            case TEST:
                registryConfig.setAddress(dubboConfig.getTestRegistryAddress());
                break;
            case BUILD:
                registryConfig.setAddress(dubboConfig.getBuildRegistryAddress());
                break;
            case PRODUCT:
                registryConfig.setAddress(dubboConfig.getProductRegistryAddress());
                break;
            default:
                throw new DocletException("无法指定dubbo注册中心地址");
        }

        //这里配置dubbo的注册中心信息，因此demo没有额外的dubbo.xml配置文件
        this.application = applicationConfig;
        this.registry = registryConfig;

    }

    public static DubboServiceFactory getInstance(EnvEnum env) {
        DubboServiceFactory factory = DUBBO_SERVICE_FACTORY_MAP.get(env);
        if (factory == null) {
            factory = new DubboServiceFactory(env);
            DUBBO_SERVICE_FACTORY_MAP.put(env, factory);
        }
        return factory;
    }

    public Object genericInvoke(String interfaceClass, String methodName, List<Map<String, Object>> parameters) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(interfaceClass); // 接口名
        reference.setGeneric(true); // 声明为泛化接口

        //ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
        //需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        //API方式编程时，容易忽略此问题。
        //这里使用dubbo内置的简单缓存工具类进行缓存

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用

        int len = parameters.size();
        String[] invokeParamTyeps = new String[len];
        Object[] invokeParams = new Object[len];
        for (int i = 0; i < len; i++) {
            invokeParamTyeps[i] = parameters.get(i).get("ParamType") + "";
            invokeParams[i] = parameters.get(i).get("Object");
        }
        return genericService.$invoke(methodName, invokeParamTyeps, invokeParams);
    }

    public static void setDubboConfig(DubboConfig dubboConfig) {
        DubboServiceFactory.dubboConfig = dubboConfig;
    }
}
