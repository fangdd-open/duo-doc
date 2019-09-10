package com.fangdd.tp.service;

import com.fangdd.tp.dto.request.ApiRequestSave;
import com.fangdd.tp.dto.request.WebDubboInvokeReq;
import com.fangdd.tp.entity.ApiRequest;
import com.fangdd.tp.entity.ApiRequestDubbo;
import com.fangdd.tp.entity.User;

import java.util.List;

/**
 * @author xuwenzhen
 * @date 18/12/4
 */
public interface ApiRequestService {
    ApiRequest save(User user, ApiRequestSave request);

    void delete(User user, String apiRequestId);

    void deleteDubbo(User user, String apiRequestId);

    List<ApiRequest> query(User user, String apiKey);

    ApiRequestDubbo saveDubbo(User user, WebDubboInvokeReq request);

    List<ApiRequestDubbo> queryDubbo(User user, String apiKey);
}
