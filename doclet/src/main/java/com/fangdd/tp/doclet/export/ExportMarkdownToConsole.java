package com.fangdd.tp.doclet.export;

import com.fangdd.tp.doclet.pojo.Chapter;
import com.fangdd.tp.doclet.render.markdown.ChapterMarkdownRender;

import java.util.List;

/**
 * @author ycoe
 * @date 18/1/21
 */
public class ExportMarkdownToConsole {
    public static void export(List<Chapter> chapterList) {
        for (int i = 0; i < chapterList.size(); i++) {
            Chapter chapter = chapterList.get(i);
            String markdown = ChapterMarkdownRender.render(chapter, i);
            System.out.println(markdown);
        }
    }
}
