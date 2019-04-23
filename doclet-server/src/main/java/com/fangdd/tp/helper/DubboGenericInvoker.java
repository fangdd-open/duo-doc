package com.fangdd.tp.helper;

import com.fangdd.tp.core.exceptions.TpServerException;
import com.fangdd.tp.dto.request.DubboGenericInvokeDto;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ycoe
 * @date 18/10/25
 */
public class DubboGenericInvoker {
    /**
     * 当前应用的信息
     */
    private static ApplicationConfig application = new ApplicationConfig();

    /**
     * 注册中心信息缓存
     */
    private static Map<String, RegistryConfig> registryConfigCache = new ConcurrentHashMap<>();

    /**
     * 各个业务方的ReferenceConfig缓存
     */
    private static Map<String, ReferenceConfig> referenceCache = new ConcurrentHashMap<>();

    static {
        application.setName("tp-doc");
    }

    /**
     * Dubbo泛接口
     *
     * @param invokeDto 调用参数
     * @return
     */
    public static Object invoke(DubboGenericInvokeDto invokeDto) {
        GenericService genericService = getGenericService(invokeDto.getInterfaceName(), invokeDto.getDubboRegistUri(), invokeDto.getVersion());

        return genericService.$invoke(invokeDto.getMethodName(), invokeDto.getMethodParamTypes(), invokeDto.getMethodParams());
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

    private static GenericService getGenericService(
            String interfaceName,
            String address,
            String version
    ) {
        String key = address + ":" + interfaceName;
        ReferenceConfig referenceConfig = referenceCache.get(key);

        if (null == referenceConfig) {
            referenceConfig = new ReferenceConfig<>();
            referenceConfig.setApplication(application);
            referenceConfig.setRegistry(getRegistryConfig(address, null, version));
            referenceConfig.setInterface(interfaceName);
            if (StringUtils.isNotEmpty(version)) {
                referenceConfig.setVersion(version);
            }
            referenceConfig.setGeneric(true);
            referenceCache.put(key, referenceConfig);
        }

        GenericService genericService;
        try {
            genericService = (GenericService) referenceConfig.get();
        } catch (Exception e) {
            referenceCache.remove(key);
            referenceConfig.destroy();
            throw new TpServerException(500, "获取GenericService失败！", e);
        }
        if (genericService == null) {
            referenceCache.remove(key);
            referenceConfig.destroy();
            throw new TpServerException(500, "GenericService创建失败");
        }
        return genericService;
    }
}
