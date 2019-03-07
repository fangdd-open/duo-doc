package com.fangdd.tp.service.impl;

import com.fangdd.tp.dao.ApiDao;
import com.fangdd.tp.dao.InvokeLogDao;
import com.fangdd.tp.dto.PagedListDto;
import com.fangdd.tp.dto.request.InvokeLogQuery;
import com.fangdd.tp.dto.request.WebDubboInvokeReq;
import com.fangdd.tp.dto.request.WebRestInvokeData;
import com.fangdd.tp.dto.response.InvokeResultDto;
import com.fangdd.tp.entity.*;
import com.fangdd.tp.service.InvokeLogService;
import com.fangdd.tp.service.UserService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xuwenzhen
 * @date 2019/2/26
 */
@Service
public class InvokeLogServiceImpl implements InvokeLogService {
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String ID = "_id";

    private static final Bson PROJECTIONS = Projections.include("apiName", "apiKey", "docId", "type", "userId", "site", "envCode", "invokeTime", "responseAtMillis", "status");
    @Autowired
    private InvokeLogDao invokeLogDao;

    @Autowired
    private ApiDao apiDao;

    @Autowired
    private UserService userService;

    /**
     * 记录请求日志
     *
     * @param user     当前请求用户
     * @param request  请求
     * @param response 响应
     */
    @Override
    @Async
    public void log(User user, WebRestInvokeData request, InvokeResultDto response) {
        InvokeLog log = new InvokeLog();
        log.setDocId(request.getDocId());
        log.setApiKey(request.getApiKey());
        log.setApiName(getApiName(request.getApiKey()));

        log.setEnvCode(request.getEnv().getCode());
        log.setUserId(user.getId());

        InvokeLogRestReq restReq = new InvokeLogRestReq();
        restReq.setUrl(request.getUrl());
        restReq.setMethod(request.getMethod());
        restReq.setPathParams(request.getPathParams());
        restReq.setParams(request.getParams());
        restReq.setHeaders(request.getHeaders());
        restReq.setRequestBody(request.getRequestBody());
        log.setRestReq(restReq);

        log.setStatus(response.getStatus());
        log.setResponseAtMillis(response.getResponseAtMillis());

        InvokeLogRestResp restResp = new InvokeLogRestResp();
        restResp.setResponseBody(response.getResponseBody());
        restResp.setHeaders(response.getHeaders());
        log.setRestResp(restResp);

        log.setInvokeTime(System.currentTimeMillis());

        log.setSite(request.getSiteId());
        log.setType(0);
        invokeLogDao.insertOne(log);
    }

    /**
     * @param user     当前请求用户
     * @param request  请求
     * @param response 响应
     */
    @Override
    @Async
    public void log(User user, WebDubboInvokeReq request, InvokeResultDto response) {
        InvokeLog log = new InvokeLog();
        log.setDocId(request.getDocId());
        log.setApiKey(request.getApiKey());
        log.setApiName(getApiName(request.getApiKey()));

        log.setEnvCode(request.getEnvCode());
        log.setUserId(user.getId());

        InvokeLogDubboReq dubboReq = new InvokeLogDubboReq();
        dubboReq.setParams(request.getParams());
        log.setDubboReq(dubboReq);

        log.setStatus(response.getStatus());
        log.setResponseAtMillis(response.getResponseAtMillis());

        InvokeLogDubboResp dubboResp = new InvokeLogDubboResp();
        dubboResp.setResponseBody(response.getResponseBody());
        log.setDubboResp(dubboResp);

        log.setInvokeTime(System.currentTimeMillis());

        log.setSite(request.getSiteId());
        log.setType(1);
        invokeLogDao.insertOne(log);
    }

    @Override
    public PagedListDto<InvokeLog> list(InvokeLogQuery query) {
        PagedListDto<InvokeLog> pagedListDto = new PagedListDto<>();
        Document filter = getFilter(query);

        long total = invokeLogDao.count(filter);
        pagedListDto.setTotal(total);
        if (total <= 0) {
            return pagedListDto;
        }

        Integer pageSize = query.getPageSize();
        if (pageSize == null || pageSize < 0) {
            pageSize = 20;
        }
        Integer pageNo = query.getPageNo();
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        int skip = (pageNo - 1) * pageSize;
        List<InvokeLog> list = invokeLogDao
                .find(filter)
                .projection(PROJECTIONS)
                .sort(Sorts.descending("invokeTime"))
                .skip(skip)
                .limit(pageSize)
                .into(Lists.newArrayList());
        pagedListDto.setList(list);
        if (!CollectionUtils.isEmpty(list)) {
            Set<Long> userIds = Sets.newHashSet();
            list.forEach(item -> userIds.add(item.getUserId()));
            if (!CollectionUtils.isEmpty(userIds)) {
                Map<Long, User> userIdMap = userService.getByIds(userIds);
                list.forEach(item -> {
                    User user = userIdMap.get(item.getUserId());
                    if (user != null) {
                        item.setUserName(user.getName());
                    }
                });
            }
        }

        return pagedListDto;
    }

    @Override
    public InvokeLog getDetail(String id) {
        return invokeLogDao.getEntity(Filters.eq(ID, id));
    }

    private Document getFilter(InvokeLogQuery query) {
        Document filter = new Document("docId", query.getDocId());
        String apiKey = query.getApiKey();
        if (!Strings.isNullOrEmpty(apiKey)) {
            filter.put("apiKey", apiKey);
        }

        String envCode = query.getEnvCode();
        if (!Strings.isNullOrEmpty(envCode)) {
            filter.put("envCode", envCode);
        }

        Integer responseStatus = query.getResponseStatus();
        if (responseStatus != null) {
            filter.put("status", responseStatus);
        }

        Integer type = query.getType();
        if (type != null) {
            filter.put("type", type);
        }

        Long userId = query.getUserId();
        if (userId != null) {
            filter.put("userId", userId);
        }

//        String endTime = query.getEndTime();
//        String startTime = query.getStartTime();

        return filter;
    }

    private String getApiName(String apiKey) {
        ApiEntity api = apiDao.find(Filters.eq(KEY, apiKey))
                .projection(Projections.include(NAME))
                .first();
        if (api != null) {
            return api.getName();
        }
        return "--";
    }
}
