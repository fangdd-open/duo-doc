package com.fangdd.tp.dao;

import com.fangdd.tp.doclet.pojo.Chapter;
import org.springframework.stereotype.Repository;

/**
 * @auth ycoe
 * @date 18/1/23
 */
@Repository
public class DocChapterDao extends TpDocBaseEntityDao<Chapter> {
    @Override
    protected String getCollectionName() {
        return "doc_chapter";
    }
}
