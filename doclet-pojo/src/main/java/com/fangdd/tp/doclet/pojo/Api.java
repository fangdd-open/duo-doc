package com.fangdd.tp.doclet.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Api implements Comparable<Api> {
    /**
     * API编码，即{className}.{methodName}
     */
    private String code;

    /**
     * API编码，全局唯一
     */
    private String key;

    /**
     * 接口类型：0=RestFul, 1=Dubbo
     */
    private Integer type;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口版本
     */
    private String version;

    /**
     * 注释
     */
    private String comment;

    /**
     * since注释
     */
    private String since;

    /**
     * author注释
     */
    private String author;

    /**
     * deprecated 注释
     */
    private String deprecated;

    /**
     * type=restFul时有效 请求方法:GET / POST / DELETE...
     */
    private List<String> methods;

    /**
     * RestFul请求路径
     */
    private List<String> paths;

    /**
     * 响应
     */
    private EntityRef response;

    /**
     * 请求参数
     */
    private List<EntityRef> requestParams;

    /**
     * 父级
     */
    @JSONField(serialize = false, deserialize = false)
    private Section section;

    /**
     * com.alibaba.dubbo.config.annotation.Service注解对应的属性(接口属性)，Dubbo接口时有值
     */
    private DubboInfo dubboInfo;

    /**
     * Dubbo接口客户端包的坐标信息，Dubbo接口时有值
     */
    private Artifact artifact;

    /**
     * 排序值，越小越前
     */
    private Integer order;

    /**
     * Graphql的DataProvider名称
     */
    private String providerName;

    /**
     * 是否批量接口，与上面的providerName配套使用
     */
    private Boolean batchProvider;

    /**
     * batchProvider=true时，多个ID的串连字符
     */
    private String idSplitter;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(String deprecated) {
        this.deprecated = deprecated;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public EntityRef getResponse() {
        return response;
    }

    public void setResponse(EntityRef response) {
        this.response = response;
    }

    public List<EntityRef> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<EntityRef> requestParams) {
        this.requestParams = requestParams;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setDubboInfo(DubboInfo dubboInfo) {
        this.dubboInfo = dubboInfo;
    }

    public DubboInfo getDubboInfo() {
        return dubboInfo;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Boolean getBatchProvider() {
        return batchProvider;
    }

    public void setBatchProvider(Boolean batchProvider) {
        this.batchProvider = batchProvider;
    }

    public String getIdSplitter() {
        return idSplitter;
    }

    public void setIdSplitter(String idSplitter) {
        this.idSplitter = idSplitter;
    }

    @Override
    public int compareTo(Api o) {
        return this.order - o.order;
    }

}
