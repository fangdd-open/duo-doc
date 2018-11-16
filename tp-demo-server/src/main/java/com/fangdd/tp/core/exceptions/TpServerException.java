package com.fangdd.tp.core.exceptions;

/**
 * @auth ycoe
 * @date 17/11/30
 */
public class TpServerException extends RuntimeException {
    /**
     * 错误状态码
     *
     * @demo 500
     */
    private Integer code;

    public TpServerException(String message) {
        super(message);
    }

    public TpServerException(Throwable ex) {
        super(ex);
    }

    public TpServerException(String message, Throwable ex) {
        super(message, ex);
    }

    public TpServerException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public TpServerException(Integer code, String message, Throwable ex) {
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
