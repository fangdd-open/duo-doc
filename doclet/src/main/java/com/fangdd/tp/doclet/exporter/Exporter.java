package com.fangdd.tp.doclet.exporter;

import com.fangdd.tp.doclet.pojo.Chapter;

import java.util.List;

/**
 *
 * @author xuwenzhen
 * @date 2019/3/11
 */
public interface Exporter {
    String exporterName();

    boolean export(List<Chapter> chapterList);
}
