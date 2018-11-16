package com.fangdd.tp.service.impl;

import com.fangdd.tp.dao.TeamDao;
import com.fangdd.tp.entity.Team;
import com.fangdd.tp.service.TeamService;
import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auth ycoe
 * @date 18/11/30
 */
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamDao teamDao;

    @Override
    public Team getByHost(String host) {
        return teamDao.find(Filters.eq("domains", host)).first();
    }
}
