package com.fangdd.tp.doclet.helper;

import java.io.IOException;

/**
 * @auth ycoe
 * @date 18/1/25
 */
public class Logger {

    public void error(String s, IOException e) {
        System.out.println("[ERROR]" + s);

    }

    public void info(String s) {
        System.out.println("[INFO]" + s);
    }
}
