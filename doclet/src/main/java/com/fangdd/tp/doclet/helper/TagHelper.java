package com.fangdd.tp.doclet.helper;

import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycoe on 16/1/27.
 */
public class TagHelper {
    private TagHelper() {
    }

    public static String getStringValue(Tag[] tags, String tagName, String defaultValue) {
        List<String> values = getValues(tags, tagName);
        if (values.isEmpty()) {
            return defaultValue;
        }
        return values.get(0);
    }

    public static List<String> getValues(Tag[] tags, String tagName) {
        List<String> values = new ArrayList<String>();
        if (tags != null && tags.length > 0) {
            for (Tag tag : tags) {
                if (tag instanceof ParamTag) {
                    if (((ParamTag) tag).parameterName().equals(tagName)) {
                        values.add(((ParamTag) tag).parameterComment());
                    }
                } else {
                    if (tag.name().equals(tagName)) {
                        values.add(tag.text());
                    }
                }
            }
        }
        return values;
    }

    /**
     * 是否包含某个tag
     *
     * @param tags
     * @param tagName
     * @return
     */
    public static boolean contendTag(Tag[] tags, String tagName) {
        if (tags == null || tags.length == 0) {
            return false;
        }
        for (Tag tag : tags) {
            if (tag.name().equals(tagName)) {
                return true;
            }
        }
        return false;
    }

    public static String getStringValue(Tag[] tags) {
        if (tags == null || tags.length == 0)
            return null;
        return tags[0].text();
    }
}
