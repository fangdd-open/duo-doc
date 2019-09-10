package com.fangdd.tp.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author xuwenzhen
 * @date 18/11/30
 */
public class UrlHelper {
    private static final Logger logger = LoggerFactory.getLogger(UrlHelper.class);

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("URLEncoder.encode失败！str={}", str, e);
        }
        return str;
    }
}
