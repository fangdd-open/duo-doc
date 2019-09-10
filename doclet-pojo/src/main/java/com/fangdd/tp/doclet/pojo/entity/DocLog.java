package com.fangdd.tp.doclet.pojo.entity;

import com.fangdd.tp.doclet.pojo.Artifact;

/**
 * @author xuwenzhen
 * @date 18/1/23
 */
public class DocLog extends Artifact {
    private String docId;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
