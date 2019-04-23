package com.fangdd.tp.controller.api;

import com.fangdd.tp.core.exceptions.Http401Exception;
import com.fangdd.tp.core.exceptions.Http403Exception;
import com.fangdd.tp.core.exceptions.TpServerException;
import com.fangdd.tp.dto.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @disable
 */
@RestController
@ControllerAdvice(annotations = {RestController.class})
public class ErrorHandleApiController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandleApiController.class);

    @ExceptionHandler(Http401Exception.class)
    public BaseResponse handleHttp401Exception(Http401Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return BaseResponse.error(401, "请登录！");
    }

    @ExceptionHandler(Http403Exception.class)
    public BaseResponse handleHttp403Exception(Http403Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return BaseResponse.error(403, "权限不足！");
    }

    @ExceptionHandler(TpServerException.class)
    public BaseResponse handleTpServerException(TpServerException ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error(ex.getMessage(), ex);
        return BaseResponse.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(500);
        logger.error("未捕获错误！", ex);
        return BaseResponse.error(500, "服务端错误，请与管理员联系！");
    }
}