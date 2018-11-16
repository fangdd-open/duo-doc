package com.fangdd.tp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @auth ycoe
 * @date 18/10/15
 */
@Configuration
@ConfigurationProperties("dubbo.conf")
public class DubboConfig {
    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 开发环境dubbo配置中心地址
     */
    private String developRegistryAddress;

    /**
     * 测试环境dubbo配置中心地址
     */
    private String testRegistryAddress;

    /**
     * 预发布环境dubbo配置中心地址
     */
    private String buildRegistryAddress;

    /**
     * 生产环境dubbo配置中心地址
     */
    private String productRegistryAddress;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDevelopRegistryAddress() {
        return developRegistryAddress;
    }

    public void setDevelopRegistryAddress(String developRegistryAddress) {
        this.developRegistryAddress = developRegistryAddress;
    }

    public String getTestRegistryAddress() {
        return testRegistryAddress;
    }

    public void setTestRegistryAddress(String testRegistryAddress) {
        this.testRegistryAddress = testRegistryAddress;
    }

    public String getBuildRegistryAddress() {
        return buildRegistryAddress;
    }

    public void setBuildRegistryAddress(String buildRegistryAddress) {
        this.buildRegistryAddress = buildRegistryAddress;
    }

    public String getProductRegistryAddress() {
        return productRegistryAddress;
    }

    public void setProductRegistryAddress(String productRegistryAddress) {
        this.productRegistryAddress = productRegistryAddress;
    }
}
