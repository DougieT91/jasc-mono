package com.tawandr.utils.object;


import com.tawandr.utils.crud.domain.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.tawandr.utils.object.StringUtils.printArray;


public class CollectionUtil {

    private static final Logger log = LoggerFactory.getLogger(CollectionUtil.class);

    public static Set listToSet(List<? extends Object> list) {
        if(objectIsNull(list)) return Collections.emptySet();
        Set<Object> set = new HashSet<>();
        try {
            set = new HashSet<>(list);
        } catch (Exception e) {
            log.error("Failed to convert LIST :: {} \nto SET. \nError :: {}", list, e.getMessage());
        }
        return set;
    }

    public static List setToList(Set<? extends Object> set) {
        if(objectIsNull(set)) return Collections.emptyList();
        List<Object> list = new ArrayList<>();
        try {
            list = new ArrayList<>(set);
        } catch (Exception e) {
            log.error("Failed to convert SET :: {} \nto LIST. \nError :: {}", set, e.getMessage());
        }
        return list;
    }

    public static int[] splitIntArrayBySpace(String intArrayString) {
        return splitStringToIntArray(intArrayString, " ");
    }

    public static int[] splitStringToIntArray(String intArrayString, String splitter) {
        String[] stringArray = intArrayString.split(splitter);
        int[] intArray = new int[stringArray.length];
        int counter = 0;
        for (String string : stringArray) {
            intArray[counter] = Integer.valueOf(string);
            counter++;
        }
        return intArray;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean arrayEquals(Object o1, Object o2) {
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[])((Object[])o1), (Object[])((Object[])o2));
        } else if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[])((boolean[])o1), (boolean[])((boolean[])o2));
        } else if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[])((byte[])o1), (byte[])((byte[])o2));
        } else if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[])((char[])o1), (char[])((char[])o2));
        } else if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[])((double[])o1), (double[])((double[])o2));
        } else if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[])((float[])o1), (float[])((float[])o2));
        } else if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[])((int[])o1), (int[])((int[])o2));
        } else if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[])((long[])o1), (long[])((long[])o2));
        } else {
            return o1 instanceof short[] && o2 instanceof short[] ? Arrays.equals((short[])((short[])o1), (short[])((short[])o2)) : false;
        }
    }

    public static boolean objectIsNull(Object object) {
        return object == null;
    }


    private static Object[] addToArray(Object[] classesArray, Object element) {
        if(classesArray==null){
            return new Object[]{element};
        }
        final int length = classesArray.length;
        Object[] newClassesArray = new Object[length +1];
        System.arraycopy(classesArray, 0, newClassesArray, 0, length);
        newClassesArray[length] = element;
        return newClassesArray;
    }

    public static <T extends BaseEntity> void updateNamedCollection(List<T> newElements, List<T> existingList) {
        for (T newElement : newElements) {
            existingList.add(newElement);

            final Optional<T> optional = existingList
                    .stream()
                    .filter(s -> s.getName().equalsIgnoreCase(newElement.getName()))
                    .findFirst();

            if (optional.isEmpty()) {
                existingList.add(newElement);
                log.debug("Adding element: {}", newElement.getName());
            } else {
                log.debug("Element already exists: {}", newElement.getName());
            }
        }
    }

    public static void updateStringCollection(List<String> newElements, List<String> existingList) {
        for (String newElement : newElements) {
            existingList.add(newElement);

            final Optional<String> optional = existingList
                    .stream()
                    .filter(s -> s.equalsIgnoreCase(newElement))
                    .findFirst();

            if (optional.isEmpty()) {
                existingList.add(newElement);
                log.debug("Adding element: {}", newElement);
            } else {
                log.debug("Element already exists: {}", newElement);
            }
        }
    }




    public static void main(String[] args) {
        Object[] ints = new Object[]{1,2,3,4};
        Object element = 5;
        ints = addToArray(ints, element);
        System.out.println(printArray(ints));

        ints = null;
        ints = addToArray(ints, element);
        System.out.println(printArray(ints));
    }

    private CollectionUtil() {
        super();
    }
}
