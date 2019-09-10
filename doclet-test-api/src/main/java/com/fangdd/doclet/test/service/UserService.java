package com.fangdd.doclet.test.service;

import com.fangdd.doclet.test.dto.User;

/**
 * @author xuwenzhen
 * @date 19/1/4
 */
public interface UserService {
    /**
     * 通过ID获取用户基本信息
     *
     * @param id 用户ID
     * @return
     */
    User get(int id);
}
