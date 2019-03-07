package com.fangdd.tp.service;

import com.fangdd.tp.entity.ApiEntity;

/**
 * @author ycoe
 * @date 19/1/21
 */
public interface ApiService {
    ApiEntity getApi(String id, String apiKey);
}
