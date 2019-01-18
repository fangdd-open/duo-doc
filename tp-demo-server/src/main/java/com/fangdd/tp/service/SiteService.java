package com.fangdd.tp.service;

import com.fangdd.tp.entity.Site;

/**
 * @auth ycoe
 * @date 18/11/30
 */
public interface SiteService {
    Site getByHost(String host);
}
