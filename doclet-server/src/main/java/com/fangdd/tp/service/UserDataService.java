package com.fangdd.tp.service;

import com.fangdd.tp.dto.request.ExpandData;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.entity.UserData;

/**
 * @author ycoe
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

    /**
     * 保存菜单展开状态
     *
     * @param data 菜单展开数据
     * @param user 当前用户
     */
    void setExpand(ExpandData data, User user);

    /**
     * 获取当前用户的配置数据
     *
     * @param user 当前用户
     * @return
     */
    UserData get(User user);
}
