package com.fangdd.tp.doclet.helper;

import com.fangdd.tp.doclet.constant.DocletConstant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ycoe on 16/1/27.
 */
public class StringHelper {
    private static final Pattern VALUE_PATTERN = Pattern.compile("^\"(.*)\"$");

    private StringHelper(){};

    public static String isNullThenSet(String str, String s) {
        if(str == null)
            return s;
        return str;
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if(str == null)
            return true;
        str = str.trim();

        if("".equals(str))
            return true;
        return false;
    }

    /**
     * 判断参数非空字符串
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 删除双引号
     * @param str
     * @return
     */
    public static String cleanQuotation(String str){
        if(isEmpty(str))
            return str;
        Matcher matcher = VALUE_PATTERN.matcher(str);
        if(matcher.find()){
            return matcher.group(1);
        }
        return str;
    }

    /**
     * 获取第一行数据
     * @param str
     * @return
     */
    public static String firstLine(String str) {
        if(isEmpty(str)){
            return null;
        }
        int index = str.indexOf("\n");
        if(index != -1){
            return str.substring(0, index).trim();
        }else {
            return str;
        }
    }

    public static String deleteFirstLine(String str) {
        if(isEmpty(str)){
            return str;
        }
        int index = str.indexOf("\n");
        if(index != -1){
            return str.substring(index + 1).trim();
        }else {
            return null;
        }
    }

    public static String loopTab(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < level; ++i){
            sb.append(DocletConstant.TAB);
        }
        return sb.toString();
    }
}
