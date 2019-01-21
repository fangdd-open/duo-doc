package com.fangdd.tp.service.impl;

import com.fangdd.tp.dao.UserDataDao;
import com.fangdd.tp.dto.request.ExpandData;
import com.fangdd.tp.entity.DocExpandingMenu;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.entity.UserData;
import com.fangdd.tp.service.UserDataService;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @auth ycoe
 * @date 19/1/17
 */
@Service
public class UserDataServiceImpl implements UserDataService {
    private static final String ID = "_id";
    private static final String FOCUS_DOC_IDS = "focusDocIds";
    private static final String EXPANDING_MENUS = "expandingMenus";

    @Autowired
    private UserDataDao userDataDao;

    /**
     * 关注文档
     *
     * @param docId 文档ID
     * @param user  当前用户
     */
    @Override
    public void focus(String docId, User user) {
        Bson userFilter = Filters.eq(ID, user.getId());
        UserData userData = userDataDao.find(userFilter)
                .projection(Projections.include(FOCUS_DOC_IDS))
                .first();
        if (userData == null) {
            userData = new UserData();
            userData.setId(user.getId());
            userData.setFocusDocIds(Lists.newArrayList(docId));
            userDataDao.insertOne(userData);
        } else {
            List<String> focusDocIds = userData.getFocusDocIds();
            if (CollectionUtils.isEmpty(focusDocIds)) {
                userDataDao.updateOne(userFilter, Updates.set(FOCUS_DOC_IDS, Lists.newArrayList(docId)));
            } else {
                if (!focusDocIds.contains(docId)) {
                    userDataDao.updateOne(userFilter, Updates.addToSet(FOCUS_DOC_IDS, docId));
                }
            }
        }
    }

    /**
     * 取消关注文档
     *
     * @param docId 文档ID
     * @param user  当前用户
     */
    @Override
    public void unfocus(String docId, User user) {
        Bson userFilter = Filters.eq(ID, user.getId());
        UserData userData = userDataDao.find(userFilter)
                .projection(Projections.include(FOCUS_DOC_IDS))
                .first();
        if (userData == null) {
            return;
        }
        List<String> focusDocIds = userData.getFocusDocIds();
        if (!focusDocIds.contains(docId)) {
            return;
        }
        focusDocIds.remove(docId);
        if (focusDocIds.isEmpty()) {
            userDataDao.updateOne(userFilter, Updates.unset(FOCUS_DOC_IDS));
        } else {
            userDataDao.updateOne(userFilter, Updates.set(FOCUS_DOC_IDS, focusDocIds));
        }
    }

    @Override
    public void setExpand(ExpandData data, User user) {
        String docId = data.getDocId();
        Bson idFilter = Filters.eq(ID, user.getId());
        Bson arrayFilter = Filters.and(idFilter, Filters.eq(EXPANDING_MENUS + ".docId", docId));
        UserData userData = userDataDao.find(arrayFilter)
                .projection(Projections.include(EXPANDING_MENUS))
                .first();
        String expandingMenus = data.getExpandingMenus();
        if (expandingMenus == null) {
            expandingMenus = "";
        }

        if (userData == null) {
            if (userDataDao.exists(idFilter)) {
                //需要追回新的文档配置即可
                userDataDao.updateOne(idFilter, Updates.push(EXPANDING_MENUS, new Document("docId", docId).append("expandingMenus", expandingMenus)));
            } else {
                //新增
                DocExpandingMenu docExpandingMenu = new DocExpandingMenu();
                docExpandingMenu.setDocId(docId);
                docExpandingMenu.setExpandingMenus(expandingMenus);
                userData = new UserData();
                userData.setId(user.getId());
                userData.setExpandingMenus(Lists.newArrayList(docExpandingMenu));
                userDataDao.insertOne(userData);
            }
        } else {
            //检查是否存在
            userDataDao.updateOne(arrayFilter, Updates.set(EXPANDING_MENUS + ".$.expandingMenus", expandingMenus));
        }
    }

    @Override
    public UserData get(User user) {
        return userDataDao.getEntity(Filters.eq(ID, user.getId()));
    }
}
