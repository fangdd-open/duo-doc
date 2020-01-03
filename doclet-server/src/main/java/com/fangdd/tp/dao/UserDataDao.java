package com.fangdd.tp.dao;

import com.fangdd.tp.entity.UserData;
import org.springframework.stereotype.Repository;

/**
 * @author xuwenzhen
 * @date 18/1/23
 */
@Repository
public class UserDataDao extends BaseDuoDocEntityDao<UserData> {
    @Override
    protected String getCollectionName() {
        return "user_data";
    }
}
