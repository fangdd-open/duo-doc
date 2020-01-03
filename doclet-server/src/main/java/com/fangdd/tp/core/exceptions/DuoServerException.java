package com.fangdd.tp.core.exceptions;

/**
 * @author xuwenzhen
 * @date 17/11/30
 */
public class DuoServerException extends RuntimeException {
    /**
     * 错误状态码
     *
     * @demo 500
     */
    private Integer code;

    public DuoServerException(String message) {
        super(message);
    }

    public DuoServerException(Throwable ex) {
        super(ex);
    }

    public DuoServerException(String message, Throwable ex) {
        super(message, ex);
    }

    public DuoServerException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public DuoServerException(Integer code, String message, Throwable ex) {
        super(message, ex);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
