package com.fangdd.tp.dao;

import com.fangdd.tp.entity.InvokeLog;
import org.springframework.stereotype.Repository;

/**
 * @auth ycoe
 * @date 18/1/23
 */
@Repository
public class InvokeLogDao extends TpDocBaseEntityDao<InvokeLog> {
    @Override
    protected String getCollectionName() {
        return "invoke_log";
    }
}
