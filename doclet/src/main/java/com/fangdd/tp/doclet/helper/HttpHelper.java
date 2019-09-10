package com.fangdd.tp.doclet.helper;

import okhttp3.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * @author xuwenzhen
 * @date 18/1/23
 */
public class HttpHelper {
    private static final Logger logger = new Logger();
    public static String post(String url, byte[] bytes) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, bytes);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Cache-Control", "no-cache")
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            logger.error("提交文档到服务器失败！url:" + url + "，body.length=" + bytes.length, e);
            return null;
        }

        if (response == null) {
            return null;
        }
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.body().byteStream(), writer, Charset.forName("UTF-8"));
        return writer.toString();
    }
}
