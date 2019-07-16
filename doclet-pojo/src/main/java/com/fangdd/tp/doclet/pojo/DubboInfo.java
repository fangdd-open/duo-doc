package com.fangdd.tp.doclet.pojo;

/**
 * com.alibaba.dubbo.config.annotation.Service注解对应的属性
 *
 * @author ycoe
 * @date 18/1/12
 */
public class DubboInfo {
    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 版本号
     */
    private String version;

    /**
     * 超时时间，单位：毫秒
     */
    private Integer timeout;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getTimeout() {
        return timeout;
    }
}
