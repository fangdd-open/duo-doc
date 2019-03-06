package com.fangdd.tp.controller.api;

import com.fangdd.tp.core.annotation.Account;
import com.fangdd.tp.dto.request.ExpandData;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.entity.UserData;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @chapter 用户接口
 * @section 用户行为接口
 * @auth ycoe
 * @date 19/1/17
 * @disable
 */
@RestController
@RequestMapping("/api/user")
public class UserDataApiController {
    @Autowired
    private UserDataService userDataService;

    @Account
    @GetMapping("/data")
    public UserData get() {
        User user = UserContextHelper.getUser();
        return userDataService.get(user);
    }

    /**
     * @param docId 文档ID
     * @return
     */
    @Account
    @GetMapping("/{docId}/focus")
    public Boolean focus(@PathVariable String docId) {
        User user = UserContextHelper.getUser();
        userDataService.focus(docId, user);
        return true;
    }

    /**
     * @param docId 文档ID
     * @return
     */
    @Account
    @DeleteMapping("/{docId}/focus")
    public Boolean unfocus(@PathVariable String docId) {
        User user = UserContextHelper.getUser();
        userDataService.unfocus(docId, user);
        return true;
    }

    /**
     * 保存菜单展开状态
     *
     * @param data 状态数据
     * @return
     */
    @Account
    @PostMapping("/menu-expand")
    public Boolean setExpand(@RequestBody ExpandData data) {
        User user = UserContextHelper.getUser();
        userDataService.setExpand(data, user);
        return true;
    }

    /**
     * 批量保存菜单展开状态
     *
     * @param data 批量状态数据
     * @return
     */
    @Account
    @PostMapping("/menu-expands")
    public Boolean setExpands(@RequestBody List<ExpandData> data) {
        User user = UserContextHelper.getUser();
        data.forEach(item -> userDataService.setExpand(item, user));
        return true;
    }
}
