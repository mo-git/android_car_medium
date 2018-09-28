package cn.bashiquan.cmj.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 集合判断
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }

    public static <E> ArrayList<E> asList(E ...e){
        return new ArrayList<>(Arrays.asList(e));
    }
}