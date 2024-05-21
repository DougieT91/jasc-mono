package com.tawandr.utils.object;

import com.tawandr.utils.object.attributes.Attribute;
import com.tawandr.utils.object.exceptions.MethodNotFoundException;
import com.tawandr.utils.object.exceptions.TypeNotFoundException;
import com.tawandr.utils.object.parameters.ParameterNameDiscoverer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import static com.tawandr.utils.object.StringUtils.printArray;


/**
 * Created By Dougie T Muringani : 3/9/2019
 */
public class MethodUtils {
    private static final ParameterNameDiscoverer pnd = new ParameterNameDiscoverer();
    private static final Logger log = LoggerFactory.getLogger(MethodUtils.class);

    public static Object[] getMethodParameters(Method method) {
        log.info("Method::\n{}", method.getName());

        final String[] parameterNames = pnd.getParameterNames(method);
        System.out.println("Parameters::" + parameterNames[0] == null ? "no param found" : parameterNames[0]);
        log.info("Parameters::\n{}", parameterNames);
        return Arrays.stream(method.getParameters())
                .map(Attribute::new)
                .toArray();
    }

    public static Method getCurrentMethod() {
        final int level = 2;
        return getMethodByLevel(level);
    }

    public static Method getMethodCallingCurrentMethod() {
        final int level = 3;
        return getMethodByLevel(level);
    }

    private static Method getMethodByLevel(int level) {
        final String methodName = new Throwable()
                .getStackTrace()[level]
                .getMethodName();

        final Class<?> aClass = getCallerClass();

        return getMethodByClassAndMethodName(aClass, methodName);
    }

    private static Class<?> getCallerClass() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        final String callerClassName;
        final Class<?> aClass;

        try {
            callerClassName = getCallerClassName(stackTraceElements);
            aClass = Class.forName(callerClassName);
        } catch (ClassNotFoundException e) {
            final String errorMessage = String.format("Error While trying to locate class:\n %s", e.getMessage());
            log.error(errorMessage);
            throw new TypeNotFoundException(errorMessage, e);
        }
        return aClass;
    }

    private static String getCallerClassName(StackTraceElement[] stackTraceElements) {
        for (int i = 1; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            final String className = stackTraceElement.getClassName();
            final boolean elementIsCurrentClass = className.equals(MethodUtils.class.getName());
            final boolean elementIsThread = className.indexOf("java.lang.Thread") != 0;
            if (!elementIsCurrentClass && elementIsThread) {
                return className;
            }
        }
        throw new TypeNotFoundException();
    }

    public static Method getMethodByClassAndMethodName(final Class<?> aClass,
                                                       final String methodName) {
        System.out.println("aClass = " + aClass + ", \nmethodName = " + methodName);
        final Method[] methods = aClass.getDeclaredMethods();

        return Arrays
                .stream(methods)
                .filter(m -> findMatchingMethodByName(m, methodName))
                .findFirst()
                .orElseThrow(() -> new MethodNotFoundException(methodName));
    }

    private static Boolean findMatchingMethodByName(Method method, String methodName) {
        final Method[] declaredMethods = MethodUtils.class.getDeclaredMethods();
        final Method localMethod = Arrays
                .stream(declaredMethods)
                .filter(dm -> dm.getName().equals(methodName))
                .findFirst()
                .orElse(null);
        return localMethod != null || method.getName().equals(methodName);
    }


    public static Class<?> getParameterType(Method method, final int parameterNaturalNumberingPosition){
        int position = parameterNaturalNumberingPosition - 1;
        return method.getParameters()[position].getType();
    }
    public static Parameter getParameter(Method method,
                                         String parameterTypeName) {
        System.out.println("MethodUtils.getParameter() method = " + method + ", parameterTypeName = " + parameterTypeName);
        final Parameter[] parameters = method.getParameters();
        System.out.println("parameters = " + printArray(parameters));
        return Arrays
                .stream(parameters)
                .filter(t -> t.getName().equals(parameterTypeName))
                .findFirst()
                .orElse(null);
    }
}

