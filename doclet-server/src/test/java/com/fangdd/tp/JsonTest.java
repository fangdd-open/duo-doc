package com.fangdd.tp;

import com.fangdd.tp.dto.request.WebDubboInvokeReq;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by xuwenzhen on 2019/9/17.
 */
public class JsonTest {
    private String json = "{\n" +
            "  \"id\": \"\",\n" +
            "  \"name\": \"测试-上海文章\",\n" +
            "  \"docId\": \"com.fangdd.cp:article-ctc-cp-server\",\n" +
            "  \"apiKey\": \"cacd7af7484badbbd9fa81cc2bf83d31\",\n" +
            "  \"envCode\": \"TEST\",\n" +
            "  \"params\": [\n" +
            "    {\n" +
            "      \"key\": \"articleId\",\n" +
            "      \"typeName\": \"long\",\n" +
            "      \"comment\": \"\",\n" +
            "      \"available\": true,\n" +
            "      \"expanding\": false,\n" +
            "      \"type\": 0,\n" +
            "      \"value\": \"36179647\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"key\": \"articleFieldsEnumss\",\n" +
            "      \"typeName\": \"com.fangdd.cp.ctc.article.enums.ArticleFieldsEnums[]\",\n" +
            "      \"comment\": \"需要获取的字段\",\n" +
            "      \"available\": true,\n" +
            "      \"expanding\": true,\n" +
            "      \"type\": 5,\n" +
            "      \"value\": [\"ID\",\"TITLE\"]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"expanding\": true,\n" +
            "  \"publicState\": true\n" +
            "}";

    static ObjectMapper objectMapper = new ObjectMapper()
            //反序列化时，忽略目标对象没有的属性
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

            //下面配置是值为null时，不显示
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)

            //下面一个配置是集合返回为空时，不显示
            .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);

    @Test
    public void test() throws IOException {
//        WebDubboInvokeReq req = JSONObject.parseObject(json, WebDubboInvokeReq.class);
        WebDubboInvokeReq req =   objectMapper.readValue(json, WebDubboInvokeReq.class);
        System.out.println(req.getName());
    }
}
