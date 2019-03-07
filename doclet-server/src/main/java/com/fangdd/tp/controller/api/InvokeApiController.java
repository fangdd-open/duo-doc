package com.fangdd.tp.controller.api;

import com.fangdd.tp.core.annotation.Account;
import com.fangdd.tp.dto.BaseResponse;
import com.fangdd.tp.dto.PagedListDto;
import com.fangdd.tp.dto.request.ApiRequestSave;
import com.fangdd.tp.dto.request.InvokeLogQuery;
import com.fangdd.tp.dto.request.WebDubboInvokeReq;
import com.fangdd.tp.dto.request.WebRestInvokeData;
import com.fangdd.tp.dto.response.InvokeResultDto;
import com.fangdd.tp.entity.ApiRequest;
import com.fangdd.tp.entity.ApiRequestDubbo;
import com.fangdd.tp.entity.InvokeLog;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.ApiRequestService;
import com.fangdd.tp.service.InvokeLogService;
import com.fangdd.tp.service.InvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @chapter 接口调用
 * @section 接口调用
 * @author ycoe
 * @date 18/11/23
 * @disable
 */
@RestController
@RequestMapping("/api/invoke")
public class InvokeApiController {
    @Autowired
    private InvokeService invokeService;

    @Autowired
    private InvokeLogService invokeLogService;

    @Autowired
    private ApiRequestService apiRequestService;

    /**
     * RestFul接口调用
     *
     * @param request 调用参数
     * @return
     */
    @Account
    @PostMapping
    public BaseResponse<InvokeResultDto> invoke(@RequestBody WebRestInvokeData request) {
        User user = UserContextHelper.getUser();
        request.setSiteId(UserContextHelper.getSite().getId());
        InvokeResultDto invokeResult = invokeService.invoke(user, request);
        if (invokeResult.getStatus() != 200) {
            return BaseResponse.error(invokeResult.getStatus(), "调用失败!", invokeResult);
        } else {
            return BaseResponse.success(invokeResult);
        }
    }

    /**
     * Dubbo接口调用
     *
     * @param request 调用参数
     * @return
     */
    @Account
    @PostMapping("/dubbo")
    public BaseResponse<InvokeResultDto> dubboInvoke(@RequestBody WebDubboInvokeReq request) {
        User user = UserContextHelper.getUser();
        request.setSiteId(UserContextHelper.getSite().getId());
        InvokeResultDto invokeResult = invokeService.dubboInvoke(user, request);
        if (invokeResult.getStatus() != 200) {
            return BaseResponse.error(invokeResult.getStatus(), "调用失败!", invokeResult);
        } else {
            return BaseResponse.success(invokeResult);
        }
    }

    /**
     * 保存接口调用参数
     *
     * @param request 调用参数
     * @return
     */
    @Account
    @PostMapping("/save/dubbo")
    public BaseResponse<ApiRequestDubbo> save(@RequestBody WebDubboInvokeReq request) {
        User user = UserContextHelper.getUser();
        request.setSiteId(UserContextHelper.getSite().getId());
        return BaseResponse.success(apiRequestService.saveDubbo(user, request));
    }

    /**
     * 保存接口调用参数
     *
     * @param request 调用参数
     * @return
     */
    @Account
    @PostMapping("/save")
    public BaseResponse<ApiRequest> save(@RequestBody ApiRequestSave request) {
        User user = UserContextHelper.getUser();
        request.setSiteId(UserContextHelper.getSite().getId());
        return BaseResponse.success(apiRequestService.save(user, request));
    }

    /**
     * 删除某个接口调用配置
     *
     * @param id 接口调用配置ID
     * @return
     */
    @Account
    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable String id) {
        User user = UserContextHelper.getUser();
        apiRequestService.delete(user, id);
        return BaseResponse.success();
    }

    /**
     * 删除某个接口调用配置
     *
     * @param id 接口调用配置ID
     * @return
     */
    @Account
    @DeleteMapping("/{id}/dubbo")
    public BaseResponse deleteDubbo(@PathVariable String id) {
        User user = UserContextHelper.getUser();
        apiRequestService.deleteDubbo(user, id);
        return BaseResponse.success();
    }

    /**
     * 获取某个Rest接口的调用参数列表
     *
     * @param apiKey 接口key
     * @return
     */
    @Account
    @GetMapping("/list/{apiKey}")
    public BaseResponse<List<ApiRequest>> list(@PathVariable String apiKey) {
        User user = UserContextHelper.getUser();
        return BaseResponse.success(apiRequestService.query(user, apiKey));
    }

    /**
     * 获取某个Dubbo接口的调用参数列表
     *
     * @param apiKey 接口key
     * @return
     */
    @Account
    @GetMapping("/list/dubbo/{apiKey}")
    public BaseResponse<List<ApiRequestDubbo>> listDubboRequests(@PathVariable String apiKey) {
        User user = UserContextHelper.getUser();
        return BaseResponse.success(apiRequestService.queryDubbo(user, apiKey));
    }

    /**
     * 查询接口调用日志
     *
     * @return
     */
    @Account
    @PostMapping("/log/list")
    public BaseResponse<PagedListDto<InvokeLog>> queryInvokeLog(@RequestBody InvokeLogQuery query) {
        return BaseResponse.success(invokeLogService.list(query));
    }

    /**
     * 查询某次接口调用日志
     *
     * @return
     */
    @Account
    @PostMapping("/log/{id}")
    public BaseResponse<InvokeLog> getDetail(@PathVariable String id) {
        return BaseResponse.success(invokeLogService.getDetail(id));
    }
}
