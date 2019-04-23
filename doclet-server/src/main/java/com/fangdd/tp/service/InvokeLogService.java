package com.fangdd.tp.service;

import com.fangdd.tp.dto.PagedListDto;
import com.fangdd.tp.dto.request.InvokeLogQuery;
import com.fangdd.tp.dto.request.WebDubboInvokeReq;
import com.fangdd.tp.dto.request.WebRestInvokeData;
import com.fangdd.tp.dto.response.InvokeResultDto;
import com.fangdd.tp.entity.InvokeLog;
import com.fangdd.tp.entity.User;

/**
 * Created by xuwenzhen on 2019/2/26.
 */
public interface InvokeLogService {
    /**
     * 记录请求日志
     * @param user 当前请求用户
     * @param request 请求
     * @param response 响应
     */
    void log(User user, WebRestInvokeData request, InvokeResultDto response);

    /**
     *
     * @param user 当前请求用户
     * @param request 请求
     * @param result 响应
     */
    void log(User user, WebDubboInvokeReq request, InvokeResultDto result);

    PagedListDto<InvokeLog> list(InvokeLogQuery query);

    InvokeLog getDetail(String id);
}
