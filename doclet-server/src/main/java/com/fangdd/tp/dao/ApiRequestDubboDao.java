package com.fangdd.tp.dao;

import com.fangdd.tp.entity.ApiRequestDubbo;
import org.springframework.stereotype.Repository;

/**
 * @author xuwenzhen
 * @date 18/1/23
 */
@Repository
public class ApiRequestDubboDao extends TpDocBaseEntityDao<ApiRequestDubbo> {
    @Override
    protected String getCollectionName() {
        return "api_request_dubbo";
    }
}
