package com.fangdd.tp.service;

import com.fangdd.tp.dto.request.LogDto;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.entity.UserLog;

/**
 * @auth ycoe
 * @date 18/12/4
 */
public interface UserLogService {
    /**
     * 添加日志
     * @param log 日志
     * @return
     */
    UserLog add(User user, LogDto log);
}
