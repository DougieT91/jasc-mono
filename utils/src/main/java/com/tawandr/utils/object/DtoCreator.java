package com.tawandr.utils.object;

import java.lang.reflect.Field;


/**
 * Created By Dougie T Muringani : 27/8/2019
 */
public class DtoCreator {

    public static String printDtoStructure(Class someObjectClass) {
        boolean classContainsNoFields = someObjectClass.getFields().length == 0;
        if (classContainsNoFields) {
            return printEmptyClass(someObjectClass);
        }
        return null;
    }

    private static String printEmptyClass(Class someObjectClass) {
        final String classSimpleName = someObjectClass.getSimpleName();
        return new StringBuilder()
                .append("public class ")
                .append(classSimpleName)
                .append("{")
                .append("\n")
                .append("}")
                .toString();
    }

    public static String printAttributeDeclaration(Field field) {
        final String fieldName = field.getName();
        final String simpleTypeName = field.getType().getSimpleName();
        return simpleTypeName + " " + fieldName;
    }

    public static void main(String[] args) {
//        final Attribute attribute = new Attribute();
//        attribute.setSimpleTypeName("String");
//        attribute.setSimpleName("testStringAttribute");
//        System.out.println(attribute);
//        attribute.createGetterAndSetter();
//        System.out.println(attribute);
    }
}

