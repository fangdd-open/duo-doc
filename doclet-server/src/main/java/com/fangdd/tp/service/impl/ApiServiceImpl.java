package com.fangdd.tp.service.impl;

import com.fangdd.tp.dao.ApiDao;
import com.fangdd.tp.entity.ApiEntity;
import com.fangdd.tp.service.ApiService;
import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuwenzhen
 * @date 19/1/21
 */
@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private ApiDao apiDao;

    @Override
    public ApiEntity getApi(String siteId, String apiKey) {
        return apiDao.find(Filters.and(
                Filters.eq("site", siteId),
                Filters.eq("key", apiKey)
        )).first();
    }
}
