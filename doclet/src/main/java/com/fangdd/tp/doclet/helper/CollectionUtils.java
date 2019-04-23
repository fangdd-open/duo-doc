package com.fangdd.tp.doclet.helper;

import java.util.Collection;

/**
 *
 * @author xuwenzhen
 * @date 2019/4/23
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
