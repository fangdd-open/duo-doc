package com.fangdd.tp.dao;

import com.fangdd.tp.entity.UserLog;
import org.springframework.stereotype.Repository;

/**
 * @auth ycoe
 * @date 18/1/23
 */
@Repository
public class UserLogDao extends TpDocBaseEntityDao<UserLog> {
    @Override
    protected String getCollectionName() {
        return "user_log";
    }
}
