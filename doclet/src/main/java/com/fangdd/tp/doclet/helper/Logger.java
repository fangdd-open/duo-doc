package com.fangdd.tp.doclet.helper;

import com.google.common.base.Throwables;

/**
 * @author xuwenzhen
 * @date 18/1/25
 */
public class Logger {
    public void error(String s, Throwable e) {
        System.out.println("[ERROR]" + s);
        if (e != null) {
            System.out.println(Throwables.getStackTraceAsString(e));
        }
    }

    public void info(String s) {
        System.out.println("[INFO]" + s);
    }
}
