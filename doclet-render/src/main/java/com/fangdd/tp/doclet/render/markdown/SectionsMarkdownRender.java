package com.fangdd.tp.doclet.render.markdown;

import com.fangdd.tp.doclet.pojo.Api;
import com.fangdd.tp.doclet.pojo.Section;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/1/15
 */
public class SectionsMarkdownRender {
    public static String render(Section section, Integer bookNo, Integer sectionNo) {
        StringBuilder sb = new StringBuilder();
        sb.append("### ");
        if(bookNo != null) {
            sb.append(bookNo + 1);
            sb.append(".");
        }
        if (sectionNo != null) {
            sb.append(sectionNo + 1);
            sb.append(". ");
        }
        sb.append(section.getName());
        sb.append("\n\n");
        List<Api> apis = section.getApis();
        if (apis != null) {
            for (int i = 0; i < apis.size(); i++) {
                Api api = apis.get(i);
                sb.append(ApiMarkdownRender.render(api, bookNo, sectionNo, i));
            }
        }
        return sb.toString();
    }
}
