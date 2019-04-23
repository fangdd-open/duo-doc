package com.fangdd.tp.doclet.exception;

/**
 * @author ycoe
 * @date 18/1/9
 */
public class DocletException extends RuntimeException {
    public DocletException(String message) {
        super(message);
    }

    public DocletException(String message, Exception e) {
        super(message, e);
    }
}
