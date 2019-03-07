package com.fangdd.tp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.tp.doclet.pojo.DubboInfo;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.fangdd.tp.doclet.pojo.entity.EnvItem;
import com.fangdd.tp.doclet.pojo.entity.RequestParam;
import com.fangdd.tp.dto.PagedListDto;
import com.fangdd.tp.dto.request.*;
import com.fangdd.tp.dto.response.InvokeResultDto;
import com.fangdd.tp.entity.*;
import com.fangdd.tp.helper.DubboGenericInvoker;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.ApiService;
import com.fangdd.tp.service.InvokeLogService;
import com.fangdd.tp.service.InvokeService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author ycoe
 * @date 18/11/23
 */
@Service
public class InvokeServiceImpl implements InvokeService {
    private static final Logger logger = LoggerFactory.getLogger(InvokeServiceImpl.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String RAW = "raw";
    private static final String X_WWW_FORM_URLENCODED = "x-www-form-urlencoded";
    private static final String FORM_DATA = "form-data";

    @Autowired
    private InvokeLogService invokeLogService;

    @Autowired
    private ApiService apiService;

    /**
     * 接口调用
     *
     * @param user 当前用户
     * @param req  接口参数
     * @return
     */
    @Override
    public InvokeResultDto invoke(User user, WebRestInvokeData req) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl httpUrl = HttpUrl.parse(req.getUrl());
        if (httpUrl == null) {
            logger.warn("调用发生错误，url异常，url={}", req.getUrl());
            return getErrorInvokeResult(System.currentTimeMillis(), "调用发生错误，url异常！");
        }

        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        if (!CollectionUtils.isEmpty(req.getParams())) {
            req.getParams().forEach(param -> urlBuilder.addEncodedQueryParameter(param.getKey(), param.getValue()));
        }

        Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build());
        if (req.getEnv() != null) {
            List<RequestParam> envHeaders = req.getEnv().getHeaders();
            addHeaders(envHeaders, requestBuilder);
        }
        addHeaders(req.getHeaders(), requestBuilder);

        if (HttpMethod.POST.name().equalsIgnoreCase(req.getMethod())) {
            // POST请求
            setPostBody(req, requestBuilder);
        }

        Request request = requestBuilder.build();
        logger.info("获取用户信息请求：{}", request);
        long t1 = System.currentTimeMillis();
        try {
            Response response = client.newCall(request).execute();
            InvokeResultDto responseResult = getResponseResult(response, t1);
            invokeLogService.log(user, req, responseResult);
            return responseResult;
        } catch (IOException e) {
            logger.warn("调用发生错误，query:{}", request.toString(), e);
            InvokeResultDto errorInvokeResult = getErrorInvokeResult(t1, e.getMessage());
            invokeLogService.log(user, req, errorInvokeResult);
            return errorInvokeResult;
        }
    }

    private void addHeaders(List<RequestParam> headers, Request.Builder requestBuilder) {
        if (!CollectionUtils.isEmpty(headers)) {
            for (RequestParam header : headers) {
                if (Strings.isNullOrEmpty(header.getKey())) {
                    continue;
                }
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }
    }

    @Override
    public InvokeResultDto dubboInvoke(User user, WebDubboInvokeReq request) {
        long t1 = System.currentTimeMillis();

        String docId = request.getDocId();
        String envCode = request.getEnvCode();

        //获取当前环境dubbo注册地址
        EnvItem dubboEnv = getDubboRegistUri(docId, envCode);
        if (dubboEnv == null) {
            return new InvokeResultDto(500, "dubbo环境不存在！", System.currentTimeMillis() - t1);
        }
        String apiKey = request.getApiKey();
        Site site = UserContextHelper.getSite();
        ApiEntity apiEntity = apiService.getApi(site.getId(), apiKey);
        if (apiEntity == null) {
            return new InvokeResultDto(500, "接口不存在！", System.currentTimeMillis() - t1);
        }
        if (!apiEntity.getType().equals(1)) {
            return new InvokeResultDto(500, "Dubbo接口不存在！", System.currentTimeMillis() - t1);
        }

        DubboInfo dubboInfo = apiEntity.getDubboInfo();
        String version = dubboInfo == null ? "" : dubboInfo.getVersion();

        List<EntityRef> requestParams = apiEntity.getRequestParams();
        int paramLen = requestParams.size();
        String[] methodParamTypes = new String[paramLen];
        Object[] methodParams = new Object[paramLen];

        List<ApiRequestDubboParamItem> params = request.getParams();
        if (params.size() != paramLen) {
            return new InvokeResultDto(500, "Dubbo接口参数数量错误！", System.currentTimeMillis() - t1);
        }
        for (int i = 0; i < paramLen; i++) {
            ApiRequestDubboParamItem reqItem = request.getParams().get(i);
            String paramTypeName = reqItem.getTypeName();
            methodParamTypes[i] = paramTypeName;
            Integer type = reqItem.getType();
            if (type == 3 || type == 4) {
                // collect | map
                int startIndex = paramTypeName.indexOf('<');
                if (startIndex > -1) {
                    methodParamTypes[i] = paramTypeName.substring(0, startIndex);
                }
            }
            methodParams[i] = getValue(reqItem);
        }

        String apiDefined = apiEntity.getCode();
        int index = apiDefined.lastIndexOf('.');
        String interfaceName = apiDefined.substring(0, index);
        String methodName = apiDefined.substring(index + 1);
        DubboGenericInvokeDto invokeDto = new DubboGenericInvokeDto();
        invokeDto.setDubboRegistUri(dubboEnv.getUrl());
        invokeDto.setInterfaceName(interfaceName);
        invokeDto.setMethodName(methodName);
        invokeDto.setVersion(version);

        invokeDto.setMethodParamTypes(methodParamTypes);
        invokeDto.setMethodParams(methodParams);

        try {
            Object resp = DubboGenericInvoker.invoke(invokeDto);
            InvokeResultDto result = new InvokeResultDto();
            result.setStatus(200);
            result.setResponseBody(getResponseBodyStr(resp));
            result.setResponseAtMillis(System.currentTimeMillis() - t1);
            result.setHeaders(Lists.newArrayList());
            invokeLogService.log(user, request, result);
            return result;
        } catch (Exception e) {
            logger.error("Dubbo接口调用失败：{}", JSONObject.toJSONString(invokeDto), e);
            InvokeResultDto invokeResultDto = new InvokeResultDto(500, e.getMessage(), System.currentTimeMillis() - t1);
            invokeLogService.log(user, request, invokeResultDto);
            return invokeResultDto;
        }
    }

    private String getResponseBodyStr(Object resp) {
        if (resp == null) {
            return null;
        }

        if (BeanUtils.isSimpleValueType(resp.getClass())) {
            //如果是基本类型
            return resp.toString();
        }

        return JSONObject.toJSONString(resp);
    }

    private Object getValue(ApiRequestDubboParamItem item) {
        String itemStrValue = item.getValue();
        if (itemStrValue == null) {
            return null;
        }
        //类型：0=基本类型 1=枚举 2=pojo 3=collection 4=map
        int type = item.getType();
        if (type == 2) {
            //pojo
            Map<String, Object> objValue = Maps.newHashMap();
            //类名
            objValue.put("class", item.getTypeName());
            List<ApiRequestDubboParamItem> fields = JSONObject.parseArray(item.getValue(), ApiRequestDubboParamItem.class);
            for (ApiRequestDubboParamItem reqItem : fields) {
                if (!reqItem.getAvailable()) {
                    continue;
                }
                Object val = getValue(reqItem);
                objValue.put(reqItem.getKey(), val);
            }
            if (objValue.isEmpty()) {
                return null;
            }
            return objValue;
        } else if (type == 3) {
            // collection
            List<ApiRequestDubboParamItem> fields = JSONObject.parseArray(itemStrValue, ApiRequestDubboParamItem.class);
            Object[] values = new Object[fields.size()];
            for (int i = 0; i < fields.size(); i++) {
                ApiRequestDubboParamItem reqItem = fields.get(i);
                Object val = getValue(reqItem);
                if (val != null) {
                    values[i] = val;
                }
            }
            return values;
        } else if (type == 4) {
            // map
            List<ApiRequestDubboParamItem> fields = JSONObject.parseArray(itemStrValue, ApiRequestDubboParamItem.class);
            Map<String, Object> map = Maps.newHashMap();
            for (ApiRequestDubboParamItem reqItem : fields) {
                Object val = getValue(reqItem);
                if (val != null) {
                    map.put(reqItem.getMapKey(), val);
                }
            }
            return map;
        }
        return itemStrValue;
    }

    private InvokeResultDto getErrorInvokeResult(long startTime, String message) {
        InvokeResultDto result = new InvokeResultDto();
        result.setStatus(0);
        result.setResponseAtMillis(System.currentTimeMillis() - startTime);
        result.setResponseBody(message);
        return result;
    }

    private EnvItem getDubboRegistUri(String docId, String envCode) {
        Site site = UserContextHelper.getSite();
        List<EnvItem> dubbos = site.getDubbo();
        if (dubbos == null) {
            return null;
        }
        for (EnvItem env : dubbos) {
            if (env.getCode().equalsIgnoreCase(envCode)) {
                return env;
            }
        }
        return null;
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

    private void setPostBody(WebRestInvokeData req, Request.Builder requestBuilder) {
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
        if (RAW.equalsIgnoreCase(rawDataType)) {
            RequestBody requestBody = RequestBody.create(mediaType, Strings.isNullOrEmpty(body.getRawData()) ? "" : body.getRawData());
            requestBuilder.post(requestBody);
            return;
        }

        if (CollectionUtils.isEmpty(body.getFormData())) {
            requestBuilder.post(null);
            return;
        }

        if (X_WWW_FORM_URLENCODED.equalsIgnoreCase(rawDataType)) {
            HttpUrl.Builder urlencodedBuilder = new HttpUrl.Builder();
            body.getFormData().forEach(form -> {
                urlencodedBuilder.addEncodedQueryParameter(form.getKey(), form.getValue());
            });
            RequestBody requestBody = RequestBody.create(mediaType, urlencodedBuilder.toString().substring(4));
            requestBuilder.post(requestBody);
        } else if (FORM_DATA.equalsIgnoreCase(rawDataType)) {
            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
            body.getFormData().forEach(form -> {
                requestBodyBuilder.addFormDataPart(form.getKey(), form.getValue());
            });
            requestBuilder.post(requestBodyBuilder.build());
        }
    }
}
