package com.fangdd.tp.doclet.exporter.impl;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.tp.doclet.DocletConfig;
import com.fangdd.tp.doclet.helper.GzipHelper;
import com.fangdd.tp.doclet.helper.HttpHelper;
import com.fangdd.tp.doclet.helper.Logger;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.google.common.base.Strings;

import java.io.IOException;
import java.util.List;

/**
 * 导出到服务器
 *
 * @author ycoe
 * @date 18/1/21
 */
public class ServerExporter extends BaseApiJsonExporter {
    private static final Logger logger = new Logger();

    @Override
    public String exporterName() {
        return "server";
    }

    @Override
    public boolean export(List<Chapter> chapterSet) {
        String docData = toJsonString(chapterSet);
        try {
            byte[] gzipData = GzipHelper.compress(docData);

            //POST to API
            String data = HttpHelper.post(DocletConfig.server + "/api/doc", gzipData);
            JSONObject json = JSONObject.parseObject(data);
            if (json == null) {
                logger.info("文档上传服务器失败！");
                //即使失败，也不去影响正常的进度
                return true;
            }
            String url = json.getString("data");
            if (Strings.isNullOrEmpty(url)) {
                logger.info("文档上传服务器失败！");
            } else {
                logger.info("Document Site: " + DocletConfig.server + url);
            }
        } catch (IOException e) {
            logger.error("将文档同步到服务器：" + DocletConfig.server + "失败！", e);
        }
        return true;
    }
}
