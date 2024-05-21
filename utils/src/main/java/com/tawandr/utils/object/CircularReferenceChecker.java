package com.tawandr.utils.object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tawandr.utils.object.StringUtils.printArray;
import static com.tawandr.utils.object.StringUtils.printSet;


/**
 * Created By Dougie T Muringani : 12/9/2019
 * <p>
 * Basic algorithm::
 * check if there is need to do a check. if there isn't just return false (hasCircularReference)
 * Otherwise, check if it is a revisit.
 * if it is stop checking and return true (hasCircularReference)
 * if not, add the object to visited set and check the object's attributes (go deeper)
 */
public class CircularReferenceChecker {

    private static final Logger log = LoggerFactory.getLogger(CircularReferenceChecker.class);

    public static boolean hasCircularReference(Object objectBeingChecked) {
        Set<Object> visited = new HashSet<>();
        return checkCircularReference(objectBeingChecked, visited);
    }

    public static boolean checkCircularReference(Object objectBeingChecked, Set<Object> visitedReferences) {
        Set<Object> visited = new HashSet<>(visitedReferences);
        final String reference = serializeReference(objectBeingChecked);
         log.trace("checking Circular Reference for Object:: {}", reference);

        boolean valueInCollectionCreatesCircularReference = checkCircularReferenceIfCollection(objectBeingChecked, visited);
        if(valueInCollectionCreatesCircularReference){
            return true;
        }

        final boolean isCircularReferenceCheckRequired = circularReferenceCheckIsRequired(objectBeingChecked, visited);
        if (isCircularReferenceCheckRequired) {
             log.trace("circular reference check is REQUIRED");
        } else {
             log.trace("NO Further checks required");
            return false;
        }

        final boolean revisited = checkRevisit(objectBeingChecked, visited);
        if (revisited) {
            return true;
        } else {
            visited = addToVisited(objectBeingChecked, visited);

            return goDeeper(objectBeingChecked, visited);
        }
    }

    private static boolean checkCircularReferenceIfCollection(Object object, Set<Object> visited) {
         log.trace("Checking if object is MAP COLLECTION or ARRAY");
        final boolean isMapCollectionOrArray = isMapCollectionOrArray(object);
        if(isMapCollectionOrArray){
             log.trace("converting object to array");
            Object[] objectArray = convertObjectToArray(object);
            return hasCircularReferenceInArray(objectArray, visited);
        }
         log.trace("object is Neither");
        return false;
    }

    private static Set<Object> addToVisited(Object object, Set<Object> visited) {
        Set<Object> updatedVisitedSet = new HashSet<>(visited);
        updatedVisitedSet.add(object);
        final String visitedGsonString = serializeReferences(updatedVisitedSet);
         log.trace("Current list of references visited Updated to::\n{}", visitedGsonString);
        return updatedVisitedSet;
    }

    private static boolean checkRevisit(Object object, Set<Object> visited) {
        return visited.contains(object);
    }

    private static boolean goDeeper(Object object, Set<Object> visited) {
        final Field[] declaredFields = object.getClass().getDeclaredFields();
        final Object[] fieldArray = new Object[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            final Object fieldValue = getFieldValue(object, field);
            fieldArray[i] = fieldValue;
        }
        return hasCircularReferenceInArray(fieldArray, visited);
    }

    private static Object getFieldValue(Object object, Field field) {
        field.setAccessible(true);
        Object fieldValue = null;
        try {
            fieldValue = field.get(object);
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException ERROR ON GET FIELD VALUE::\n{}", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException ERROR ON GET FIELD VALUE::\n{}", e);
        }
        return fieldValue;
    }   // Todo: [13/9/2019] : move to ObjectUtils

    private static Object[] convertObjectToArray(Object object) {
        final Object[] objectDefaultArray = new Object[1];
        objectDefaultArray[0] = object;

        if(!isMapCollectionOrArray(object)){
            return objectDefaultArray;
        }

        final boolean isMap = object instanceof Map<?,?>;
        final boolean isArray = object.getClass().isArray();
        final boolean isCollection = object instanceof Collection<?>;

        if(isArray){
             log.trace("object is Array");
            return (Object[]) object;
        }else if(isCollection){
             log.trace("object is Collection");
            final Collection<?> objectCollection = (Collection<?>) object;
            final int size = objectCollection.size();
            return objectCollection.toArray(new Object[size]);
        }else if(isMap){
             log.trace("object is Map, converting it to Collection...");
            final Map<?, ?> objectMap = (Map<?, ?>) object;
            final Collection<?> objectCollection = objectMap.values();
            return convertObjectToArray(objectCollection);
        }
        return objectDefaultArray;
    }   // Todo: [15/9/2019] : Move to Collection Utils

    private static boolean isMapCollectionOrArray(Object object) {
        final boolean isNotNull = object != null;
        final boolean isArray = isNotNull && object.getClass().isArray();
        final boolean isCollection = isNotNull && object instanceof Collection<?>;
        final boolean isMap = isNotNull && object instanceof Map<?,?>;
        return isArray || isCollection || isMap;
    }   // Todo: [15/9/2019] : Move to Collection Utils

    private static boolean hasCircularReferenceInArray(Object[] objectArray, Set<Object> visited) {
        for (Object object : objectArray) {
            final boolean hasCircularReference = checkCircularReference(object, visited);
            if (hasCircularReference) {
                final String fieldValueReference = serializeReference(object);
                 log.trace("[{}] has been Referenced", fieldValueReference);
                return true;
            }
        }
        return false;
    }

    private static String serializeReference(Object object) {
        if(object == null){
            return "null";
        }
        return object.getClass().getName() + "@" + Integer.toHexString(object.hashCode());
    }   // Todo: [13/9/2019] : move to ObjectUtils

    private static String serializeReferences(Collection<Object> objectSet) {
        final HashSet<String> objectReferenceSet = objectSet
                .stream()
                .map(o -> serializeReference(o))
                .collect(Collectors.toCollection(HashSet::new));
        return printSet(objectReferenceSet);
    }   // Todo: [13/9/2019] : move to ObjectUtils

    private static boolean circularReferenceCheckIsRequired(Object objectBeingChecked, Set<Object> visited) {
        if (objectBeingChecked == null) {
             log.trace("the [objectBeingChecked] provided is NULL");
            return false;
        }
        if (visited == null) {
             log.trace("the visited set is empty.");
            return false;
        }

        final boolean isClass = objectBeingChecked instanceof Class;
        if (isClass) {
             log.trace("circular reference check is NOT REQUIRED for A CLASS");
            return false;
        }

        final boolean isJavaLanguageClass = isFromJavaLanguageSpecification(objectBeingChecked);

        if (isJavaLanguageClass) {
             log.trace("JLS Association FOUND");
            return false;
        } else {
             log.trace("NONE Found");
            return true;
        }
    }

    private static boolean isFromJavaLanguageSpecification(Object object) {
        return isJavaLanguageSpecificationClass(object.getClass());
    }   // Todo: [13/9/2019] : move to ObjectUtils

    private static boolean isJavaLanguageSpecificationClass(Class aClass) {
        final String packageName = aClass.getPackage().getName();
         log.trace("Scanning package [{}] for java Language Specification (JLS) Association", packageName);
        return isFromJavaPackage(packageName)
                || isFromJavaxPackage(packageName);
    }   // Todo: [13/9/2019] : move to ObjectUtils

    private static boolean isFromJavaPackage(String objectPackageName) {
        String[] javaPackageSubPackages = new String[]{
                "java.applet",
                "java.awt",
                "java.beans",
                "java.io",
                "java.lang",
                "java.math",
                "java.net",
                "java.nio",
                "java.rmi",
                "java.security",
                "java.sql",
                "java.text",
                "java.time",
                "java.util"
        };

        return containsMatch(objectPackageName, javaPackageSubPackages);
    }   // Todo: [13/9/2019] : move to ObjectUtils

    private static boolean isFromJavaxPackage(String objectPackageName) {
        String[] javaxPackageSubPackages = new String[]{
                "javax.accessibility",
                "javax.activation",
                "javax.activity",
                "javax.annotation",
                "javax.imageio",
                "javax.jws",
                "javax.model",
                "javax.management",
                "javax.naming",
                "javax.net",
                "javax.print",
                "javax.rmi",
                "javax.script",
                "javax.security",
                "javax.smartcardio",
                "javax.sound",
                "javax.sql",
                "javax.swing",
                "javax.tools",
                "javax.transaction",
                "javax.xml",
        };

        return containsMatch(objectPackageName, javaxPackageSubPackages);
    }   // Todo: [13/9/2019] : move to ObjectUtils

    private static boolean containsMatch(String packageName, String[] jLSPackages) {
        final String serializedJlsPackages = printArray(jLSPackages);
        log.trace("java language specification packages:: \n{}", serializedJlsPackages);
        for (String jLSPackagePrefix : jLSPackages) {
            if (packageName.startsWith(jLSPackagePrefix)) {
                 log.trace("::::::::: Associated with JLS Package [{}]", jLSPackagePrefix);
                return true;
            }
        }
        return false;
    }   // Todo: [13/9/2019] : move to StringUtils

    private CircularReferenceChecker() {
        super();
    }
}
