package com.fangdd.tp.dto.request;

/**
 * @author ycoe
 * @date 19/1/21
 */
public class DubboGenericInvokeDto {
    /**
     * dubbo注册服务器地址，比如zookeeper地址
     */
    private String dubboRegistUri;

    /**
     * 接口服务类名（包含包路径）
     */
    private String interfaceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 接口版本号
     */
    private String version;

    /**
     * 方法参数类型
     */
    private String[] methodParamTypes;

    /**
     * 方法参数
     */
    private Object[] methodParams;

    public String getDubboRegistUri() {
        return dubboRegistUri;
    }

    public void setDubboRegistUri(String dubboRegistUri) {
        this.dubboRegistUri = dubboRegistUri;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getMethodParamTypes() {
        return methodParamTypes;
    }

    public void setMethodParamTypes(String[] methodParamTypes) {
        this.methodParamTypes = methodParamTypes;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(Object[] methodParams) {
        this.methodParams = methodParams;
    }
}
