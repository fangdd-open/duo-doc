package com.fangdd.tp.service;

import com.fangdd.tp.dto.request.DocCreateDto;
import com.fangdd.tp.dto.request.LogDto;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.entity.UserLog;

/**
 * @author ycoe
 * @date 18/12/4
 */
public interface UserLogService {
    /**
     * 添加日志
     * @param log 日志
     * @return
     */
    UserLog add(User user, LogDto log);

    /**
     * 文档创建
     * @param docCreateDto 创建日志
     * @return
     */
    UserLog add(DocCreateDto docCreateDto);
}
