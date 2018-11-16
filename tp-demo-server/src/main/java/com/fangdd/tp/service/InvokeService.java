package com.fangdd.tp.service;

import com.fangdd.tp.dto.request.InvokeData;
import com.fangdd.tp.dto.response.InvokeResultDto;
import com.fangdd.tp.entity.User;

/**
 * @auth ycoe
 * @date 18/11/23
 */
public interface InvokeService {
    /**
     * 接口调用
     *
     *
     * @param user
     * @param request 接口参数
     * @return
     */
    InvokeResultDto invoke(User user, InvokeData request);
}
