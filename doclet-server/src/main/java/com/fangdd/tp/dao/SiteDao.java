package com.fangdd.tp.dao;

import com.fangdd.tp.entity.Site;
import org.springframework.stereotype.Repository;

/**
 * @author ycoe
 * @date 18/11/28
 */
@Repository
public class SiteDao extends TpDocBaseEntityDao<Site> {
    /**
     * 获取Collection名称
     *
     * @return
     */
    @Override
    protected String getCollectionName() {
        return "site";
    }
}
