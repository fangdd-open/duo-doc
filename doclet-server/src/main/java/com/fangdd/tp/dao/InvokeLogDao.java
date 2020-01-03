package com.fangdd.tp.dao;

import com.fangdd.tp.entity.InvokeLog;
import org.springframework.stereotype.Repository;

/**
 * @author xuwenzhen
 * @date 18/1/23
 */
@Repository
public class InvokeLogDao extends BaseDuoDocEntityDao<InvokeLog> {
    @Override
    protected String getCollectionName() {
        return "invoke_log";
    }
}
