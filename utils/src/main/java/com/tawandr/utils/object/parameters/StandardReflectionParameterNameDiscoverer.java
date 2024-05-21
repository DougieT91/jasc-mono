package com.tawandr.utils.object.parameters;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class StandardReflectionParameterNameDiscoverer extends ParameterNameDiscoverer {
    public StandardReflectionParameterNameDiscoverer() {
    }

   
    public String[] getParameterNames(Method method) {
        return this.getParameterNames(method.getParameters());
    }

   
    public String[] getParameterNames(Constructor<?> ctor) {
        return this.getParameterNames(ctor.getParameters());
    }

   
    private String[] getParameterNames(Parameter[] parameters) {
        String[] parameterNames = new String[parameters.length];

        for(int i = 0; i < parameters.length; ++i) {
            Parameter param = parameters[i];
            if (!param.isNamePresent()) {
                return null;
            }

            parameterNames[i] = param.getName();
        }

        return parameterNames;
    }
}
