package com.fangdd.tp.doclet.render.markdown;

import com.fangdd.tp.doclet.pojo.Api;

/**
 * @author ycoe
 * @date 18/1/15
 */
public class ApiMarkdownRender {
    public static String render(Api api, Integer bookNo, Integer sectionNo, Integer apiNo) {
        StringBuilder sb = new StringBuilder();
        sb.append("#### ");
        if (bookNo != null) {
            sb.append(bookNo + 1);
            sb.append(".");
        }
        if (sectionNo != null) {
            sb.append(sectionNo + 1);
            sb.append(".");
        }
        if (apiNo != null) {
            sb.append(apiNo + 1);
            sb.append(". ");
        }
        sb.append("[");
        sb.append(getApiType(api.getType()));
        sb.append("]");
        sb.append(api.getName());
        sb.append("\n\n");

        if (api.getType() == 0) {
            sb.append(RestApiMarkdownRender.render(api));
        } else if (api.getType() == 1) {
            sb.append(DubboApiMarkdownRender.render(api));
        }

        sb.append("\n\n");
        return sb.toString();
    }

    private static String getApiType(Integer apiType) {
        return apiType == 0 ? "RestFul" : "Dubbo";
    }
}
