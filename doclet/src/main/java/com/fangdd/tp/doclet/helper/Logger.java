package com.fangdd.tp.doclet.helper;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author xuwenzhen
 * @date 18/1/25
 */
public class Logger {
    public void error(String s, Exception e) {
        System.out.println("[ERROR]" + s);
        if (e != null) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            System.out.println(stackTrace);
        }
    }

    public void info(String s) {
        System.out.println("[INFO]" + s);
    }
}
