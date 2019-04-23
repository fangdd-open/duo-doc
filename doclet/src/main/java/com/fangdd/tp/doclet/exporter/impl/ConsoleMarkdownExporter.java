package com.fangdd.tp.doclet.exporter.impl;

import com.fangdd.tp.doclet.exporter.Exporter;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.fangdd.tp.doclet.render.markdown.ChapterMarkdownRender;

import java.util.List;

/**
 * @author ycoe
 * @date 18/1/21
 */
public class ConsoleMarkdownExporter implements Exporter {
    @Override
    public String exporterName() {
        return "console";
    }

    @Override
    public boolean export(List<Chapter> chapterList) {
        for (int i = 0; i < chapterList.size(); i++) {
            Chapter chapter = chapterList.get(i);
            String markdown = ChapterMarkdownRender.render(chapter, i);
            System.out.println(markdown);
        }
        return true;
    }
}
