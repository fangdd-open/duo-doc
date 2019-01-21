package com.fangdd.tp.dao;

import com.fangdd.tp.entity.ApiRequest;
import org.springframework.stereotype.Repository;

/**
 * @auth ycoe
 * @date 18/1/23
 */
@Repository
public class ApiRequestDao extends TpDocBaseEntityDao<ApiRequest> {
    @Override
    protected String getCollectionName() {
        return "api_request";
    }
}
