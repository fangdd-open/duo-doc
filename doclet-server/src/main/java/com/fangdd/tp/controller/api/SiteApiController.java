package com.fangdd.tp.controller.api;

import com.fangdd.tp.entity.Site;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuwenzhen
 * @date 19/1/18
 * @disable
 */
@RestController
@RequestMapping("/api/site")
public class SiteApiController {
    @Autowired
    private SiteService siteService;

    /**
     * 获取当前请求对应的网站信息
     *
     * @return
     */
    @GetMapping
    public Site get() {
        return UserContextHelper.getSite();
    }
}
