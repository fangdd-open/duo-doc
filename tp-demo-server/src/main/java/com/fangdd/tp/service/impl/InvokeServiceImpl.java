package com.fangdd.tp.service.impl;

import com.fangdd.tp.dto.request.InvokeData;
import com.fangdd.tp.dto.request.RequestBodyParam;
import com.fangdd.tp.dto.request.RequestParam;
import com.fangdd.tp.dto.response.InvokeResultDto;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.service.InvokeService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * @auth ycoe
 * @date 18/11/23
 */
@Service
public class InvokeServiceImpl implements InvokeService {
    private static final Logger logger = LoggerFactory.getLogger(InvokeServiceImpl.class);
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * 接口调用
     *
     * @param user
     * @param req  接口参数
     * @return
     */
    @Override
    public InvokeResultDto invoke(User user, InvokeData req) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(req.getUrl()).newBuilder();

        if (!CollectionUtils.isEmpty(req.getParams())) {
            req.getParams().forEach(param -> urlBuilder.addEncodedQueryParameter(param.getKey(), param.getValue()));
        }

        Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build());
        if (!CollectionUtils.isEmpty(req.getHeaders())) {
            for (RequestParam header : req.getHeaders()) {
                if (Strings.isNullOrEmpty(header.getKey())) {
                    continue;
                }
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }

        if (HttpMethod.POST.name().equalsIgnoreCase(req.getMethod())) {
            // POST请求
            setPostBody(req, requestBuilder);
        }

        Request request = requestBuilder.build();

        logger.info("获取用户信息请求：{}", request);

        long t1 = System.currentTimeMillis();
        try {
            Response response = client.newCall(request).execute();
            return getResponseResult(response, t1);
        } catch (IOException e) {
            InvokeResultDto result = new InvokeResultDto();
            result.setStatus(0);
            result.setResponseAtMillis(System.currentTimeMillis() - t1);
            logger.warn("调用发生错误，query:{}", request.toString(), e);
            result.setResponseBody(e.getMessage());
            return result;
        }
    }

    private InvokeResultDto getResponseResult(Response response, long t) {
        InvokeResultDto result = new InvokeResultDto();
        result.setResponseAtMillis(System.currentTimeMillis() - t);
        result.setStatus(response.code());
        try {
            ResponseBody body = response.body();
            if (body != null) {
                result.setResponseBody(body.string());
            }
        } catch (IOException e) {
            logger.error("获取response body失败！", e);
        }
        Headers headers = response.headers();
        if (headers.size() > 0) {
            List<RequestParam> headerList = Lists.newArrayList();
            for (String name : headers.names()) {
                RequestParam header = new RequestParam();
                header.setKey(name);
                header.setValue(headers.get(name));
                headerList.add(header);
            }
            result.setHeaders(headerList);
        }
        result.setReceivedResponseAtMillis(response.receivedResponseAtMillis());
        result.setSentRequestAtMillis(response.sentRequestAtMillis());
        return result;
    }

    private void setPostBody(InvokeData req, Request.Builder requestBuilder) {
        String mediaTypeStr = "application/json";
        if (!CollectionUtils.isEmpty(req.getHeaders())) {
            for (RequestParam header : req.getHeaders()) {
                if (CONTENT_TYPE.equalsIgnoreCase(header.getKey())) {
                    mediaTypeStr = header.getValue();
                    break;
                }
            }
        }
        RequestBodyParam body = req.getRequestBody();
        if (body == null) {
            MediaType mediaType = MediaType.parse(mediaTypeStr);
            RequestBody requestBody = RequestBody.create(mediaType, "");
            requestBuilder.post(requestBody);
            return;
        }

        MediaType mediaType = MediaType.parse(mediaTypeStr);
        String rawDataType = body.getRawDataType();
        if ("raw".equalsIgnoreCase(rawDataType)) {
            RequestBody requestBody = RequestBody.create(mediaType, Strings.isNullOrEmpty(body.getRawData()) ? "" : body.getRawData());
            requestBuilder.post(requestBody);
            return;
        }

        if (CollectionUtils.isEmpty(body.getFormData())) {
            requestBuilder.post(null);
            return;
        }

        if ("x-www-form-urlencoded".equalsIgnoreCase(rawDataType)) {
            HttpUrl.Builder urlencodedBuilder = new HttpUrl.Builder();
            body.getFormData().forEach(form -> {
                urlencodedBuilder.addEncodedQueryParameter(form.getKey(), form.getValue());
            });
            RequestBody requestBody = RequestBody.create(mediaType, urlencodedBuilder.toString().substring(4));
            requestBuilder.post(requestBody);
        } else if ("form-data".equalsIgnoreCase(rawDataType)) {
            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
            body.getFormData().forEach(form -> {
                requestBodyBuilder.addFormDataPart(form.getKey(), form.getValue());
            });
            requestBuilder.post(requestBodyBuilder.build());
        }
    }
}
