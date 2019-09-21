package com.fangdd.tp.doclet.helper;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

/**
 * @author xuwenzhen
 * @date 18/3/12
 */
public class FileHelper {
    private static final String TURN_LINE = "\n";

    public static String readFileToString(File file) {
        try {
            return Joiner.on(TURN_LINE).join(Files.readLines(file, Charsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
