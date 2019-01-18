package com.fangdd.tp.service;

import com.fangdd.tp.dao.UserLogDao;
import com.fangdd.tp.dto.request.InvokeData;
import com.fangdd.tp.dto.request.LogDto;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.entity.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auth ycoe
 * @date 18/12/4
 */
@Service
public class UserLogServiceImpl implements UserLogService {
    @Autowired
    private UserLogDao userLogDao;

    @Override
    public UserLog add(User user, LogDto log) {
        if (user == null) {
            //没有用户信息！
            return null;
        }
        UserLog userLog = new UserLog();
        userLog.setSite(user.getCurrentSite().getId());
        userLog.setUserId(user.getId());
        userLog.setAction(log.getAction().getAction());
        userLog.setTitle(log.getAction().getActionName());
        InvokeData invokeRequest = log.getInvokeRequest();
        if (invokeRequest != null) {
            userLog.setApiId(invokeRequest.getApiKey());
        }
        userLog.setRequest(invokeRequest);
        userLog.setActionTime(System.currentTimeMillis());
        userLogDao.insertOne(userLog);
        return userLog;
    }
}
