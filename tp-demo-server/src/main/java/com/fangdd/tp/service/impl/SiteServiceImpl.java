package com.fangdd.tp.service.impl;

import com.fangdd.tp.dao.SiteDao;
import com.fangdd.tp.entity.Site;
import com.fangdd.tp.service.SiteService;
import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auth ycoe
 * @date 18/11/30
 */
@Service
public class SiteServiceImpl implements SiteService {
    @Autowired
    private SiteDao siteDao;

    @Override
    public Site getByHost(String host) {
        return siteDao.find(Filters.eq("domains", host)).first();
    }
}
