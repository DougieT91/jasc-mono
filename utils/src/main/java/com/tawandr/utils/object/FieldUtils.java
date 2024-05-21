package com.tawandr.utils.object;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class FieldUtils {

    List<String> stringListExampleField = new ArrayList<String>();
    List<Integer> integerListExampleField = new ArrayList<Integer>();
    private static final Logger log = LoggerFactory.getLogger(FieldUtils.class);

    public static void main(String... args) throws Exception {
        Field stringListField = FieldUtils.class.getDeclaredField("stringListExampleField");
        getFieldGenericTypeName(stringListField);
        Field integerListField = FieldUtils.class.getDeclaredField("integerListExampleField");
        getFieldGenericTypeName(integerListField);
    }

    public static String getFieldGenericTypeName(Field field) {
        Class<?> aClass = getFieldGenericType(field);
        final String simpleName = aClass.getSimpleName();
        System.out.println("Generic Type = " + simpleName);
        return simpleName;
    }

    private static Class<?> getFieldGenericType(Field field) {
        final ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        return (Class<?>) genericType.getActualTypeArguments()[0];
    }
}

