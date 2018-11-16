package com.fangdd.tp.helper;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.fangdd.tp.core.DocletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auth ycoe
 * @date 18/10/25
 */
public class DubboCallbackUtils {
    private static Logger logger = LoggerFactory.getLogger(DubboCallbackUtils.class);

    // 当前应用的信息
    private static ApplicationConfig application = new ApplicationConfig();

    // 注册中心信息缓存
    private static Map<String, RegistryConfig> registryConfigCache = new ConcurrentHashMap<>();

    // 各个业务方的ReferenceConfig缓存
    private static Map<String, ReferenceConfig> referenceCache = new ConcurrentHashMap<>();

    static {
        application.setName("consumer-test");
    }

    /**
     * 获取注册中心信息
     *
     * @param address zk注册地址
     * @param group   dubbo服务所在的组
     * @return
     */
    private static RegistryConfig getRegistryConfig(String address, String group, String version) {
        String key = address + "-" + group + "-" + version;
        RegistryConfig registryConfig = registryConfigCache.get(key);
        if (null == registryConfig) {
            registryConfig = new RegistryConfig();
            if (StringUtils.isNotEmpty(address)) {
                registryConfig.setAddress(address);
            }
            if (StringUtils.isNotEmpty(version)) {
                registryConfig.setVersion(version);
            }
            if (StringUtils.isNotEmpty(group)) {
                registryConfig.setGroup(group);
            }
            registryConfigCache.put(key, registryConfig);
        }
        return registryConfig;
    }

    private static ReferenceConfig getReferenceConfig(
            String interfaceName,
            String address,
            String group,
            String version
    ) {
        String referenceKey = interfaceName;
        ReferenceConfig referenceConfig = referenceCache.get(referenceKey);
        if (null == referenceConfig) {
            try {
                referenceConfig = new ReferenceConfig<>();
                referenceConfig.setApplication(application);
                referenceConfig.setRegistry(getRegistryConfig(address, group, version));
                Class interfaceClass = Class.forName(interfaceName);
                referenceConfig.setInterface(interfaceClass);
                if (StringUtils.isNotEmpty(version)) {
                    referenceConfig.setVersion(version);
                }
                referenceConfig.setGeneric(true);
                //referenceConfig.setUrl("dubbo://10.1.50.167:20880/com.test.service.HelloService");
                referenceCache.put(referenceKey, referenceConfig);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return referenceConfig;
    }

    public static Object invoke(String interfaceName, String methodName, List<Object> paramList, String address, String version) {
        ReferenceConfig reference = getReferenceConfig(interfaceName, address, null, version);
        GenericService genericService = (GenericService) reference.get();
        if (genericService == null) {
            logger.debug("GenericService 不存在:{}", interfaceName);
            return null;
        }

        Object[] paramObject = null;
        if (!CollectionUtils.isEmpty(paramList)) {
            paramObject = new Object[paramList.size()];
            for (int i = 0; i < paramList.size(); i++) {
                paramObject[i] = paramList.get(i);
            }
        }

        return genericService.$invoke(methodName, getMethodParamType(interfaceName, methodName), paramObject);
    }


    public static String[] getMethodParamType(String interfaceName, String methodName) {
        try {
            //创建类
            Class<?> class1 = Class.forName(interfaceName);
            //获取所有的公共的方法
            Method[] methods = class1.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] paramClassList = method.getParameterTypes();
                    String[] paramTypeList = new String[paramClassList.length];
                    for (int i = 0; i < paramClassList.length; i++) {
                        Class className = paramClassList[i];
                        paramTypeList[i] = className.getTypeName();
                    }
                    return paramTypeList;
                }
            }
        } catch (Exception e) {
            throw new DocletException("获取方法参数类型失败！", e);
        }
        return null;

    }
}
