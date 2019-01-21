package com.fangdd.tp.dao;

import com.fangdd.tp.entity.ApiEntity;
import org.springframework.stereotype.Repository;

/**
 * @auth ycoe
 * @date 18/1/23
 */
@Repository
public class ApiDao extends TpDocBaseEntityDao<ApiEntity> {
    @Override
    protected String getCollectionName() {
        return "api";
    }
}
