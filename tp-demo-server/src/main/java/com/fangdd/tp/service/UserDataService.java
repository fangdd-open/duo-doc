package com.fangdd.tp.service;

import com.fangdd.tp.dto.request.ExpandData;
import com.fangdd.tp.entity.User;

/**
 * @auth ycoe
 * @date 19/1/17
 */
public interface UserDataService {
    /**
     * 关注文档
     *
     * @param docId 文档ID
     * @param user  当前用户
     */
    void focus(String docId, User user);

    /**
     * 取消关注文档
     *
     * @param docId 文档ID
     * @param user  当前用户
     */
    void unfocus(String docId, User user);

    void setExpand(ExpandData data, User user);
}
