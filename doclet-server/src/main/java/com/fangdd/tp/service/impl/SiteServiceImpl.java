package com.fangdd.tp.service.impl;

import com.fangdd.tp.dao.SiteDao;
import com.fangdd.tp.entity.Site;
import com.fangdd.tp.service.SiteService;
import com.fangdd.tp.service.UserService;
import com.fangdd.traffic.common.mongo.utils.UUIDUtils;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuwenzhen
 * @date 18/11/30
 */
@Service
public class SiteServiceImpl implements SiteService {
    private static final Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);
    @Autowired
    private SiteDao siteDao;

    @Autowired
    private UserService userService;

    /**
     * 通过域名获取站点信息
     *
     * @param domain 域名
     * @return 站点信息
     */
    @Override
    public Site getByHost(String domain) {
        Bson filter = Filters.eq("domains", domain);
        return siteDao.find(filter).first();
    }

    /**
     * 初始化站点
     *
     * @param domain 域名
     * @return 初始化完成的站点信息
     */
    @Override
    public Site init(String domain) {
        String uuid = UUIDUtils.generateUUID();

        Site site = new Site();
        String siteId = domain.replaceAll("\\.", "_");
        site.setId(siteId);
        site.setSalt(uuid.substring(0, 12));
        site.setPublicKey(uuid.substring(12));
        site.setName(domain);
        site.setLogo("http://static.duoec.com/logo/logo-white-bg.png");
        site.setDomains(Lists.newArrayList(domain));
        site.setAuths(Lists.newArrayList("pwd"));
        siteDao.insertOne(site);
        logger.info("初始化站点: id={}, domain={}", siteId, domain);

        userService.init(site);
        return site;
    }

    /**
     * 检查站点是否未初始化过
     *
     * @return 是否未初始化过
     */
    @Override
    public boolean isEmpty() {
        return siteDao.count() <= 0;
    }
}
