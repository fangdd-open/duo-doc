package com.fangdd.tp.helper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author xuwenzhen
 * @date 2019/12/20
 */
public class CacheHelper {
    public static Cache<String, ?> CACHE = CacheBuilder.newBuilder()
            //1分钟后过期
            .expireAfterWrite(1, TimeUnit.MINUTES)
            //最多1万个key
            .maximumSize(10000)
            .build();
}
