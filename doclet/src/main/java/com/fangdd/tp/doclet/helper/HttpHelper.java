package com.fangdd.tp.doclet.helper;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        if (response.body() == null) {
            logger.error("提交文档到服务器失败，响应内容为空！url:" + url + "，body.length=" + bytes.length, null);
            return null;
        }
        InputStream in = response.body().byteStream();
        return CharStreams.toString(new InputStreamReader(in, Charsets.UTF_8));
    }
}
