package com.fangdd.tp.dto.request;

import com.fangdd.tp.doclet.pojo.entity.RequestParam;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/11/23
 */
public class RequestBodyParam {
    /**
     * 由POST提交过去的数据
     */
    private List<RequestParam> formData;

    /**
     * POST提交过去的数据
     */
    private String rawData;

    /**
     * POST提交过去的raw数据类型
     */
    private String rawDataType;

    public List<RequestParam> getFormData() {
        return formData;
    }

    public void setFormData(List<RequestParam> formData) {
        this.formData = formData;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getRawDataType() {
        return rawDataType;
    }

    public void setRawDataType(String rawDataType) {
        this.rawDataType = rawDataType;
    }
}
