package com.fangdd.tp.doclet.helper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author ycoe
 * @date 18/3/12
 */
public class FileHelper {
    public static String readFileToString(File file) {
        try {
            return FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
