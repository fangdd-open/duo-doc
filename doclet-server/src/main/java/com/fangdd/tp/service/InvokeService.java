package com.fangdd.tp.service;

import com.fangdd.tp.dto.request.WebDubboInvokeReq;
import com.fangdd.tp.dto.request.WebRestInvokeData;
import com.fangdd.tp.dto.response.InvokeResultDto;
import com.fangdd.tp.entity.User;

/**
 * @author ycoe
 * @date 18/11/23
 */
public interface InvokeService {
    /**
     * RestFul接口调用
     *
     * @param user    当前用户
     * @param request 接口参数
     * @return
     */
    InvokeResultDto invoke(User user, WebRestInvokeData request);

    /**
     * Dubbo接口调用
     *
     * @param user    当前用户
     * @param request 接口参数
     * @return
     */
    InvokeResultDto dubboInvoke(User user, WebDubboInvokeReq request);
}
