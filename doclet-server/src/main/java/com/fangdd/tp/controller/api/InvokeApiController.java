package com.fangdd.tp.controller.api;

import com.fangdd.tp.core.annotation.Account;
import com.fangdd.tp.dto.BaseResponse;
import com.fangdd.tp.dto.request.ApiRequestSave;
import com.fangdd.tp.dto.request.WebDubboInvokeReq;
import com.fangdd.tp.dto.request.WebRestInvokeData;
import com.fangdd.tp.dto.response.InvokeResultDto;
import com.fangdd.tp.entity.ApiRequest;
import com.fangdd.tp.entity.ApiRequestDubbo;
import com.fangdd.tp.entity.User;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.ApiRequestService;
import com.fangdd.tp.service.InvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @chapter 接口调用
 * @section 接口调用
 * @auth ycoe
 * @date 18/11/23
 */
@RestController
@RequestMapping("/api/invoke")
public class InvokeApiController {
    @Autowired
    private InvokeService invokeService;

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
     * 获取某个接口的调用配置列表
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

}
