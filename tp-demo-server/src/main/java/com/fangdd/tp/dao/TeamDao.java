package com.fangdd.tp.dao;

import com.fangdd.tp.entity.Team;
import org.springframework.stereotype.Repository;

/**
 * @auth ycoe
 * @date 18/11/28
 */
@Repository
public class TeamDao extends TpDocBaseEntityDao<Team> {
    /**
     * 获取Collection名称
     *
     * @return
     */
    @Override
    protected String getCollectionName() {
        return "team";
    }
}
