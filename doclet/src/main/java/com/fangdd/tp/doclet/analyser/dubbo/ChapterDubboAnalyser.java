package com.fangdd.tp.doclet.analyser.dubbo;

import com.fangdd.tp.doclet.constant.DocletConstant;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.helper.StringHelper;
import com.fangdd.tp.doclet.helper.TagHelper;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.fangdd.tp.doclet.pojo.Section;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Tag;

/**
 * 分析Dubbo接口
 * @author ycoe
 * @date 18/1/12
 */
public class ChapterDubboAnalyser {
    public static Section analyse(ClassDoc classDoc) {
        Tag[] tags = classDoc.tags();
        String chapterName = TagHelper.getStringValue(tags, "@chapter", DocletConstant.DEFAULT_CHAPTER_NAME);
        Chapter chapter = BookHelper.getChapter(chapterName);

        String sectionName = TagHelper.getStringValue(tags, "@section", null);
        String comment = classDoc.commentText();
        if (StringHelper.isEmpty(sectionName)) {
            //尝试使用注释第一行
            if (StringHelper.isNotEmpty(comment)) {
                sectionName = StringHelper.firstLine(comment);
                comment = StringHelper.deleteFirstLine(comment);
            }
        }
        //类全名
        String classFullName = classDoc.qualifiedTypeName();
        if (StringHelper.isEmpty(sectionName)) {
            sectionName = classFullName;
        }

        Section section = BookHelper.getSections(chapter, sectionName);
        section.setCode(classFullName);

        Integer order = TagHelper.getIntegerValue(tags, "@c2");
        if (order != null) {
            section.setOrder(order);
        }
        section.setComment(comment);
        return section;
    }
}
