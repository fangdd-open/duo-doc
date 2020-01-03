package com.fangdd.tp.service.impl;

import com.fangdd.tp.core.exceptions.DuoServerException;
import com.fangdd.tp.dao.ApiRequestDao;
import com.fangdd.tp.dao.ApiRequestDubboDao;
import com.fangdd.tp.dto.request.*;
import com.fangdd.tp.entity.ApiRequest;
import com.fangdd.tp.entity.ApiRequestDubbo;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.enums.RoleEnum;
import com.fangdd.tp.enums.UserActionEnum;
import com.fangdd.tp.service.ApiRequestService;
import com.fangdd.tp.service.UserLogService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuwenzhen
 * @date 18/12/4
 */
@Service
public class ApiRequestServiceImpl implements ApiRequestService {
    @Autowired
    private ApiRequestDao apiRequestDao;

    @Autowired
    private ApiRequestDubboDao apiRequestDubboDao;

    @Autowired
    private UserLogService userLogService;

    @Override
    public ApiRequest save(User user, ApiRequestSave request) {
        String apiRequestId = request.getId();
        ApiRequest existsApiRequest = null;
        if (!Strings.isNullOrEmpty(apiRequestId)) {
            existsApiRequest = apiRequestDao.getEntityById(apiRequestId);
            if (existsApiRequest == null) {
                throw new DuoServerException(404, "记录找不到或已被删除！");
            }
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setApiKey(request.getApiKey());
        apiRequest.setName(request.getName());
        apiRequest.setPublicState(request.getPublicState());
        apiRequest.setUserId(user.getId());
        apiRequest.setUserName(user.getName());
        apiRequest.setEnvCode(request.getEnv().getCode());
        apiRequest.setUrl(request.getUrl());
        apiRequest.setMethod(request.getMethod());
        apiRequest.setPathParams(request.getPathParams());
        apiRequest.setParams(request.getParams());
        apiRequest.setHeaders(request.getHeaders());
        apiRequest.setRequestBody(request.getRequestBody());
        if (existsApiRequest == null) {
            apiRequestDao.insertOne(apiRequest);
        } else {
            apiRequest.setId(apiRequestId);
            apiRequestDao.updateEntity(apiRequest);
        }

        LogDto log = new LogDto();
        log.setAction(UserActionEnum.INVOKE_REQUEST_SAVE);
        WebRestInvokeData invokeRequest = new WebRestInvokeData();
        BeanUtils.copyProperties(request, invokeRequest);
        invokeRequest.setApiKey(request.getId());
        log.setInvokeRequest(invokeRequest);
        userLogService.add(user, log);

        return apiRequest;
    }

    @Override
    public void delete(User user, String apiRequestId) {
        ApiRequest apiRequest = apiRequestDao.getEntityById(apiRequestId);
        if (apiRequest == null) {
            throw new DuoServerException(404, "无法找到当前配置记录：id=" + apiRequestId);
        }
        if (!user.getId().equals(apiRequest.getUserId())) {
            //记录不是当前用户创建的，检查是否是管理员
            if (user.getRole() != RoleEnum.ADMIN.getRole()) {
                throw new DuoServerException(500, "没有权限删除此配置");
            }
        }

        apiRequestDao.deleteOne(Filters.eq("_id", apiRequestId));
        // 写入操作日志
        LogDto log = new LogDto();
        log.setAction(UserActionEnum.INVOKE_REQUEST_DELETE);
        userLogService.add(user, log);
    }

    @Override
    public void deleteDubbo(User user, String apiRequestId) {
        ApiRequestDubbo apiRequestDubbo = apiRequestDubboDao.getEntityById(apiRequestId);
        if (apiRequestDubbo == null) {
            throw new DuoServerException(404, "无法找到当前配置记录：id=" + apiRequestId);
        }
        if (!user.getId().equals(apiRequestDubbo.getUserId())) {
            //记录不是当前用户创建的，检查是否是管理员
            if (user.getRole() != RoleEnum.ADMIN.getRole()) {
                throw new DuoServerException(500, "没有权限删除此配置");
            }
        }

        apiRequestDubboDao.deleteOne(Filters.eq("_id", apiRequestId));
        // 写入操作日志
        LogDto log = new LogDto();
        log.setAction(UserActionEnum.INVOKE_REQUEST_DUBBO_DELETE);
        userLogService.add(user, log);
    }

    @Override
    public List<ApiRequest> query(User user, String apiKey) {
        Bson filter = Filters.and(
                Filters.eq("apiKey", apiKey),
                Filters.or(
                        Filters.eq("publicState", true),
                        Filters.eq("userId", user.getId())
                )
        );
        return apiRequestDao.find(filter)
                .limit(200)
                .into(Lists.newArrayList());
    }

    @Override
    public ApiRequestDubbo saveDubbo(User user, WebDubboInvokeReq request) {
        String apiRequestId = request.getId();
        ApiRequestDubbo existsApiRequest = null;
        if (!Strings.isNullOrEmpty(apiRequestId)) {
            existsApiRequest = apiRequestDubboDao.getEntityById(apiRequestId);
            if (existsApiRequest == null) {
                throw new DuoServerException(404, "记录找不到或已被删除！");
            }
        }

        ApiRequestDubbo apiRequest = new ApiRequestDubbo();
        apiRequest.setName(request.getName());
        apiRequest.setDocId(request.getDocId());
        apiRequest.setApiKey(request.getApiKey());
        apiRequest.setEnvCode(request.getEnvCode());
        apiRequest.setParams(request.getParams());
        apiRequest.setPublicState(request.getPublicState());

        if (existsApiRequest == null) {
            apiRequest.setUserId(user.getId());
            apiRequest.setUserName(user.getName());
            apiRequestDubboDao.insertOne(apiRequest);
        } else {
            apiRequest.setId(request.getId());
            apiRequestDubboDao.updateEntitySafe(apiRequest);
        }

        LogDto log = new LogDto();
        log.setAction(UserActionEnum.INVOKE_REQUEST_DUBBO_SAVE);
        log.setInvokeRequestDubbo(apiRequest);
        userLogService.add(user, log);

        return apiRequest;
    }

    @Override
    public List<ApiRequestDubbo> queryDubbo(User user, String apiKey) {
        Bson filter = Filters.and(
                Filters.eq("apiKey", apiKey),
                Filters.or(
                        Filters.eq("publicState", 1),
                        Filters.eq("userId", user.getId())
                )
        );
        return apiRequestDubboDao.find(filter)
                .limit(200)
                .into(Lists.newArrayList());
    }
}
