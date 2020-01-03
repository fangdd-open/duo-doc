package com.fangdd.tp.service;

import com.fangdd.tp.entity.Site;

/**
 * @author xuwenzhen
 * @date 18/11/30
 */
public interface SiteService {
    /**
     * 通过域名获取站点信息
     *
     * @param domain 域名
     * @return 站点信息
     */
    Site getByHost(String domain);

    /**
     * 初始化站点
     *
     * @param domain 域名
     * @return 初始化完成的站点信息
     */
    Site init(String domain);

    /**
     * 检查站点是否未初始化过
     * @return 是否未初始化过
     */
    boolean isEmpty();
}
