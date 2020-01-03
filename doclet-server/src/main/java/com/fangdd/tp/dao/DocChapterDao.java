package com.fangdd.tp.dao;

import com.fangdd.tp.doclet.pojo.Chapter;
import org.springframework.stereotype.Repository;

/**
 * @author xuwenzhen
 * @date 18/1/23
 */
@Repository
public class DocChapterDao extends BaseDuoDocEntityDao<Chapter> {
    @Override
    protected String getCollectionName() {
        return "doc_chapter";
    }
}
