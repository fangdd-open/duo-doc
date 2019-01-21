package com.fangdd.tp.dto;

/**
 * 接口响应体
 * Created by ycoe on 17/6/25.
 */
public class BaseResponse<T> {
    public static final Integer CODE_SUCCESS = 200;

    public static final Integer CODE_NOT_FIND = 404;

    public static final Integer CODE_FORBID = 403;

    public static final Integer CODE_PARAM_INVALID = 500;

    public static final Integer CODE_SERVER_ERROR = 503;

    /**
     * 响应状态码：200=正常 404=记录找不到 403=权限不足 500=参数有误 503=未知错误
     * @demo 200
     */
    private Integer code;

    /**
     * 返回数据
     * @demo {...}
     */
    private T data;

    /**
     * 返回信息，一般是code不等于200时，返回异常信息
     * @demo 标题不能为空！
     */
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static BaseResponse success() {
        return success(null);
    }

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(CODE_SUCCESS);
        if (data != null) {
            response.setData(data);
        }
        return response;
    }

    public static <T> BaseResponse<T> error(int code, String message) {
        return error(code, message, null);
    }

    public static <T> BaseResponse<T> error(int code, String message, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(code);
        if (message != null) {
            response.setMessage(message);
        }
        if (data != null) {
            response.setData(data);
        }
        return response;
    }

    /**
     * 记录找不到
     *
     * @param message 错误信息
     * @return
     */
    public static <T>BaseResponse<T> notFindError(String message) {
        return error(CODE_NOT_FIND, message);
    }

    /**
     * 没有权限错误
     *
     * @param message 错误信息
     * @return
     */
    public static <T>BaseResponse<T> forbidError(String message) {
        return error(CODE_FORBID, message);
    }

}
