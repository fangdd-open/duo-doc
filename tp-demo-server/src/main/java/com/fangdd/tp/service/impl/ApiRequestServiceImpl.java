package com.fangdd.tp.service.impl;

import com.fangdd.tp.core.exceptions.TpServerException;
import com.fangdd.tp.dao.ApiRequestDao;
import com.fangdd.tp.dto.request.ApiRequestSave;
import com.fangdd.tp.dto.request.InvokeData;
import com.fangdd.tp.dto.request.LogDto;
import com.fangdd.tp.entity.ApiRequest;
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
 * @auth ycoe
 * @date 18/12/4
 */
@Service
public class ApiRequestServiceImpl implements ApiRequestService {
    @Autowired
    private ApiRequestDao apiRequestDao;

    @Autowired
    private UserLogService userLogService;

    @Override
    public ApiRequest save(User user, ApiRequestSave request) {
        String apiRequestId = request.getId();
        ApiRequest existsApiRequest = null;
        if (!Strings.isNullOrEmpty(apiRequestId)) {
            existsApiRequest = apiRequestDao.getEntityById(apiRequestId);
            if (existsApiRequest == null) {
                throw new TpServerException(404, "记录找不到或已被删除！");
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
        InvokeData invokeRequest = new InvokeData();
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
            throw new TpServerException(404, "无法找到当前配置记录：id=" + apiRequestId);
        }
        if (!user.getId().equals(apiRequest.getUserId())) {
            //记录不是当前用户创建的，检查是否是管理员
            if (user.getRole() != RoleEnum.ADMIN.getRole()) {
                throw new TpServerException(500, "没有权限删除此配置");
            }
        }

        apiRequestDao.deleteOne(Filters.eq("_id", apiRequestId));
        // 写入操作日志
        LogDto log = new LogDto();
        log.setAction(UserActionEnum.INVOKE_REQUEST_DELETE);
        userLogService.add(user, log);
    }

    @Override
    public List<ApiRequest> query(User user, String apiKey) {
        Bson filter = Filters.and(
                Filters.eq("apiKey", apiKey),
                Filters.or(
                        Filters.eq("publicState", 1),
                        Filters.eq("userId", user.getId())
                )
        );
        return apiRequestDao.find(filter)
                .limit(200)
                .into(Lists.newArrayList());
    }
}
