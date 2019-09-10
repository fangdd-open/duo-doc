package com.fangdd.tp.dto;

import com.fangdd.tp.entity.Site;
import com.fangdd.tp.entity.User;

/**
 * @author xuwenzhen
 * @date 18/11/30
 */
public class UserContent {
    private User user;

    private Site site;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
