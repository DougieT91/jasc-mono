package com.tawandr.utils.object.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created By Dougie T Muringani : 23/2/2020
 */
public class ElvisOperator {
    private ElvisOperator() {
        super();
    }

    private static <T> T coalesce(T actualValue,
                                  T defaultValue) {
        if (actualValue == null) {
            return defaultValue;
        }
        return actualValue;
    }

    public static <T> T elvisOf(T actualValue,
                                T defaultValue) {
        return coalesce(actualValue, defaultValue);
    }

    private static <T1, T2> T2 coalesce(final T1 parentObject,
                                        final Function<T1, T2> function) {
        if (parentObject == null) {
            return null;
        }
        return function.apply(parentObject);
    }

    public static <T1, T2> T2 elvisOf(final T1 parentObject,
                                      final Function<T1, T2> function) {
        return coalesce(parentObject, function);
    }

    public static <T1, T2, T3> T3 elvisOf(T1 parentObject,
                                          Function<T1, T2> function1,
                                          Function<T2, T3> function2) {
        return elvisOf(elvisOf(parentObject, function1), function2);
    }

    public static <T1, T2, T3, T4> T4 elvisOf(T1 parentObject,
                                              Function<T1, T2> function1,
                                              Function<T2, T3> function2,
                                              Function<T3, T4> function3) {
        return elvisOf(elvisOf(parentObject, function1, function2), function3);
    }

    public static <T1, T2, T3, T4, T5> T5 elvisOf(T1 parentObject,
                                                  Function<T1, T2> function1,
                                                  Function<T2, T3> function2,
                                                  Function<T3, T4> function3,
                                                  Function<T4, T5> function4) {
        return elvisOf(elvisOf(parentObject, function1, function2, function3), function4);
    }

    public static String elvisOf(final String stringToReplaceWithEmptyIfNull) {
        return coalesce(stringToReplaceWithEmptyIfNull, "");
    }

    public static <K, V> Map<K, V> elvisOf(final Map<K, V> mapToReplaceWithEmptyIfNull) {
        return coalesce(mapToReplaceWithEmptyIfNull, new HashMap<>());
    }

    public static <T> Collection<T> elvisOf(final Collection<T> collectionToReplaceWithEmptyIfNull) {
        return coalesce(collectionToReplaceWithEmptyIfNull, Collections.emptyList());
    }
}

