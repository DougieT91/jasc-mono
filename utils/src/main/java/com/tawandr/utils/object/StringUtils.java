package com.tawandr.utils.object;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StringUtils extends org.springframework.util.StringUtils{

    public static void main(String[] args) {
        String string = "REQUESTED_RANGE_NOT_SATISFIABLE";
        System.out.println(string);
        System.out.println(toSentence(string));
    }

    public static String toSentence(String string) {
        final String toPascalCase = toPascalCase(string);
        return splitCamelCase(toPascalCase);
    }

    public static String toPascalCase(String string) {
        if(isSnakeCase(string)){
            return snakeToPascalCase(string);
        }
        if (isEmpty(string)) {
            string = "object";  // Todo: [29/8/2019] : Find better replacement
        }
        if (isPascalCase(string)) {
            return string;
        }
        string = camelToPascalCase(string);
        return string;
    }

    private static String snakeToPascalCase(String string) {
        String[] wordArray = string.split("_");
        String[] capitalizedWordArray = toTitleCase(wordArray);
        return String.join("", capitalizedWordArray);
    }

    private static String[] toTitleCase(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].toLowerCase();
            strings[i] = capitalizeFirstLetter(strings[i]);
        }
        return strings;
    }

    private static String camelToPascalCase(String string) {
        final String[] wordArray = string.split(" ");
        final String firstWord = capitalizeFirstLetter(wordArray[0]);
        wordArray[0] = firstWord;
        return String.join("", wordArray);
    }

    public static String capitalizeFirstLetter(String word) {
        final String[] firstWordCharacterArray = word.split("");
        firstWordCharacterArray[0] = firstWordCharacterArray[0].toUpperCase();
        return String.join("", firstWordCharacterArray);
    }

    public static String[] capitalizeFirstLetter(String[] words) {
        for (int i = 0; i < words.length; i++) {
            words[i] = capitalizeFirstLetter(words[i]);
        }
        return words;
    }

    public static String snakeToCamel(final String str) {
        final String lowerCase = str.toLowerCase();
        final String pascal = lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1);

        StringBuilder builder = new StringBuilder(pascal);

        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(
                        i, i + 1,
                        String.valueOf(
                                Character.toUpperCase(
                                        builder.charAt(i))));
            }
        }
        return builder.toString();
    }

    public static String splitCamelCase(String camel) {
        StringBuilder builder = new StringBuilder();
        for (String word : camel.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            builder.append(word).append(" ");
        }
        final int length = builder.length();
        builder.deleteCharAt(length -1);
        return builder.toString();
    }

    public static String[] convertUnixPathsToPackages(String[] paths) {
        String[] packages = new String[paths.length];
        for (int i = 0; i < paths.length; i++) {
            packages[i] = convertUnixPathToPackage(paths[i]);
        }
        return packages;
    }

    public static String[] convertPackagesToUnixPaths(String[] packages) {
        String[] paths = new String[packages.length];
        for (int i = 0; i < packages.length; i++) {
            paths[i] = convertUnixPathToPackage(packages[i]);
        }
        return paths;
    }

    public static String convertUnixPathToPackage(String path) {
        return path.replaceAll("/", ".");
    }

    public static String convertPackageToUnixPath(String packageName) {
        return packageName.replaceAll("\\.", "/");
    }

    public static String joinPackages(String... packages) {
        StringBuilder jointPackageNames = new StringBuilder();
        for (String packageName : packages) {
            packageName = convertUnixPathToPackage(packageName);
            jointPackageNames
                    .append(packageName)
                    .append("\\.");
        }
        jointPackageNames.deleteCharAt(jointPackageNames.length()-1);
        return jointPackageNames.toString();
    }

    public static String appendAnd(String query) {
        if (query.endsWith("WHERE ")) {
            return query;
        } else {
            return query += " AND ";
        }
    }

    private static boolean isPascalCase(String string) {
        return Character.isUpperCase(string.charAt(0))
                && !string.contains("_")
                && !string.contains("-")
                && !isSpacedSentenceString(string)
                && !(isAllUpperCase(string) && string.length()>1);
    }

    private static boolean isCamelCase(String string) {
        return Character.isLowerCase(string.charAt(0))
                && !string.contains("_")
                && !string.contains("-")
                && !isSpacedSentenceString(string)
                && !(isAllUpperCase(string) && string.length()>1);
    }

    private static boolean isKebabCase(String string) {
        return isAllLowerCase(string)
                && !string.contains("_")
                && !isSpacedSentenceString(string);
    }

    private static boolean isSnakeCase(String string) {
        return !string.contains("-")
                && !isSpacedSentenceString(string)
                && string.contains("_");
    }

    private static boolean isAllUpperCase(String string) {
        for(char c : string.toCharArray()) {
            if(isLetter(c) && isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAllLowerCase(String string) {
        for(char c : string.toCharArray()) {
            if(isLetter(c) && isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSpacedSentenceString(String string) {
        return string.contains(" ");
    }

    private static boolean isLowerCase(char c) {
        return (int)c >= 97  && (int)c <= 122;
    }

    private static boolean isUpperCase(char c) {
        return (int)c >= 65  && (int)c <= 90;
    }

    private static boolean isLetter(char c) {
        return isUpperCase(c) || isLowerCase(c);
    }

    private static boolean isSymbol(char c) {
        return !isLetter(c) && (int)c > 31;
    }


    public static boolean isNotEmpty(String attribute) {
        if (attribute == null || attribute.trim().isEmpty() || "All".equalsIgnoreCase(attribute.trim())) {
            return false;
        } else {
            return true;
        }
    }


    public static boolean isEmpty(String attribute) {
        if (attribute == null || attribute.trim().isEmpty() || "All".equalsIgnoreCase(attribute.trim())) {
            return true;
        } else {
            return false;
        }
    }


    @SuppressWarnings("Duplicates")
    public static String getValueByKey(String key, String rules) throws Exception {
        String packageName = null;
        try {
            if (rules == null || rules.isEmpty()) {
                return packageName;
            }
            String[] tokens = rules.split(",");
            Map<String, String> map = new HashMap<String, String>();
            String[] innerTokens;
            for (String token : tokens) {
                innerTokens = token.split(":");
                map.put(innerTokens[0], innerTokens[1]);
                innerTokens = null;
            }

            packageName = map.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return packageName;
    }

    @SuppressWarnings("Duplicates")
    public static List<String> getCollectionFromRuleValue(final String propertyValue) {

        if (propertyValue == null || propertyValue.isEmpty()) {
            return null;
        }
        String[] values = propertyValue.split(",");
        List<String> results = null;
        if (values != null && values.length > 0) {
            results = new ArrayList<String>();
        }
        String value;
        for (String temp : values) {
            value = temp != null ? temp.trim() : temp;
            results.add(value);

        }
        return results;
    }

    public static String printSet(Set objects) {
        return printList(new ArrayList(objects));
    }


    public static String printList(List objects) {
        if (objects == null) {
            return null;
        }

        if (objects.isEmpty()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();

        builder.append("[\n");
        objects.forEach(object -> {
            builder.append(object.toString()).append(",\n");
        });
        final int lastIndexOfComma = builder.lastIndexOf(",");
        builder.delete(lastIndexOfComma, lastIndexOfComma + 1);
        builder.append("]");
        return builder.toString();
    }

    public static String printSimpleList(List objects) {
        if (objects == null) {
            return null;
        }

        if (objects.isEmpty()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();

        builder.append("[");
        objects.forEach(object -> {
            builder.append(object.toString()).append(", ");
        });
        final int lastIndexOfComma = builder.lastIndexOf(",");
        builder.delete(lastIndexOfComma, lastIndexOfComma + 1);
        builder.append("]");
        return builder.toString();
    }


    public static String printStream(Stream<?> objects) {
        if (objects == null) {
            return null;
        }
        return printList(objects.collect(Collectors.toList()));
    }

    public static String printIntArray(int[] intArray) {
        List<Integer> integerList = new ArrayList<>();
        if (intArray == null) {
            return "[]";
        }
        for (int element : intArray) {
            integerList.add(element);
        }
        return printSimpleList(integerList);
    }

    public static String printArray(Object[] objects) {
        if (objects == null) {
            return "[]";
        }
        List<Object> objectList = new ArrayList<>(Arrays.asList(objects));
        return printList(objectList);
    }

    @SneakyThrows
    public static String toJsonStringTrimmed(Object object) {
        if (object==null) {
            return "{}";
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.findAndRegisterModules();

        return objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    public static String toJsonString(Object object) {
        if (object==null) {
            return "{}";
        }
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
