package com.fangdd.tp.doclet.render.markdown;

import com.fangdd.tp.doclet.pojo.Chapter;
import com.fangdd.tp.doclet.pojo.Section;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/1/15
 */
public class ChapterMarkdownRender {
    public static String render(Chapter chapter, Integer bookNo) {
        StringBuilder sb = new StringBuilder();
        sb.append("## ");
        if(bookNo != null) {
            sb.append(bookNo + 1);
            sb.append(". ");
        }
        sb.append(chapter.getName());
        sb.append("\n\n");

        List<Section> sections = chapter.getSections();
        if(sections != null) {
            for (int i = 0; i < sections.size(); i++) {
                Section section = sections.get(i);
                sb.append(SectionsMarkdownRender.render(section, bookNo, i));
            }
        }

        return sb.toString();
    }
}
