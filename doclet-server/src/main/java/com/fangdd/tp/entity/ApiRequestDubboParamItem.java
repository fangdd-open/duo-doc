package com.fangdd.tp.entity;

/**
 * 从web前端发起的dubbo请求
 *
 * @author xuwenzhen
 * @date 19/1/24
 */
public class ApiRequestDubboParamItem {
    /**
     * 参数名称
     */
    private String key;

    /**
     * 参数值，有可能是一个对象的json字符串
     */
    private String value;

    /**
     * 参数类型名称
     */
    private String typeName;

    /**
     * 类型：0=基本类型 1=枚举 2=pojo 3=collection 4=map
     */
    private Integer type;

    /**
     * 是否有效
     */
    private Boolean available;

    /**
     * 如果是Map时的key
     */
    private String mapKey;

    /**
     * 是否多行展示，String类型编辑时有效
     */
    private Boolean multiLine;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public Boolean getMultiLine() {
        return multiLine;
    }

    public void setMultiLine(Boolean multiLine) {
        this.multiLine = multiLine;
    }
}
