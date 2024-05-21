package com.tawandr.utils.object;

import com.tawandr.utils.object.attributes.Attribute;

import java.lang.reflect.Field;
import java.util.Arrays;

import static com.tawandr.utils.object.CollectionUtil.arrayEquals;


/**
 * Created By Dougie T Muringani : 3/9/2019
 */
public class ObjectUtil {
    public static int nullSafeHashCode(Object obj) {
        if (obj == null) {
            return 0;
        } else {
            if (obj.getClass().isArray()) {
                if (obj instanceof Object[]) {
                    return nullSafeHashCode((Object[])((Object[])obj));
                }

                if (obj instanceof boolean[]) {
                    return nullSafeHashCode((boolean[])((boolean[])obj));
                }

                if (obj instanceof byte[]) {
                    return nullSafeHashCode((byte[])((byte[])obj));
                }

                if (obj instanceof char[]) {
                    return nullSafeHashCode((char[])((char[])obj));
                }

                if (obj instanceof double[]) {
                    return nullSafeHashCode((double[])((double[])obj));
                }

                if (obj instanceof float[]) {
                    return nullSafeHashCode((float[])((float[])obj));
                }

                if (obj instanceof int[]) {
                    return nullSafeHashCode((int[])((int[])obj));
                }

                if (obj instanceof long[]) {
                    return nullSafeHashCode((long[])((long[])obj));
                }

                if (obj instanceof short[]) {
                    return nullSafeHashCode((short[])((short[])obj));
                }
            }

            return obj.hashCode();
        }
    }

    public static boolean nullSafeEquals( Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        } else if (o1 != null && o2 != null) {
            if (o1.equals(o2)) {
                return true;
            } else {
                return o1.getClass().isArray() && o2.getClass().isArray() ? arrayEquals(o1, o2) : false;
            }
        } else {
            return false;
        }
    }

    public static String getIdClassType(Class clazz) {
        final Attribute idAttribute = getIdAttribute(clazz);
        return idAttribute.getTypeName();
    }

    public static Attribute getIdAttribute(Class clazz) {
        if(clazz==null){
            return new Attribute();
        }
        final Attribute[] attributes = getAttributes(clazz);
        return Arrays.stream(attributes)
                .filter(attribute -> attribute.getSimpleName().equals("id"))
                .findFirst()
                .orElse(new Attribute());
    }

    public static Attribute[] getAttributes(Class clazz) {
        final Field[] fields = clazz.getFields();
        final Attribute[] attributes = new Attribute[fields.length];

        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = new Attribute(fields[i]);
        }
        return attributes;
    }
}