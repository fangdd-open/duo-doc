package com.fangdd.tp.controller.api;

import com.fangdd.tp.core.annotation.Account;
import com.fangdd.tp.core.exceptions.TpServerException;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.enums.RoleEnum;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @chapter Rest接口
 * @section 文档接口
 * @author xuwenzhen
 * @date 18/1/18
 * @disable
 */
@RestController
@RequestMapping("/api/doc")
public class DocOwnerApiController {
    @Autowired
    private UserService userService;

    /**
     * 认领为某个文档的所有者
     *
     * @return
     */
    @Account(RoleEnum.USER)
    @PostMapping("/{docId}/owner")
    public Boolean addAsDocOwner(@PathVariable String docId) {
        User user = UserContextHelper.getUser();
        if (user == null) {
            throw new TpServerException(500, "用户不能为空！");
        }
        return userService.addDocOwner(docId, user.getId());
    }

    /**
     * 取消某个文档的所有者身份
     *
     * @return
     */
    @Account(RoleEnum.USER)
    @DeleteMapping("/{docId}/owner")
    public Boolean deleteDocOwner(@PathVariable String docId) {
        User user = UserContextHelper.getUser();
        if (user == null) {
            throw new TpServerException(500, "用户不能为空！");
        }
        return userService.removeDocOwner(docId, user.getId());
    }

    /**
     * 设置文档的所有者（管理员）
     *
     * @return
     */
    @Account(RoleEnum.ADMIN)
    @PostMapping("/{docId}/owner/{userId:\\d+}")
    public Boolean setAsDocOwner(@PathVariable String docId, @PathVariable long userId) {
        return userService.addDocOwner(docId, userId);
    }

    /**
     * 移除某个文档的所有者（管理员）
     *
     * @return
     */
    @Account(RoleEnum.ADMIN)
    @DeleteMapping("/{docId}/owner/{userId:\\d+}")
    public Boolean removeDocOwner(@PathVariable String docId, @PathVariable long userId) {
        return userService.removeDocOwner(docId, userId);
    }
}
