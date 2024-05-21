package com.tawandr.utils.object.parameters;

import java.lang.reflect.Parameter;

public class RuntimeParameterDemo {
    
    public static void main(String[] args) {
    }
    
    public static void printParam(Parameter p) {
    System.out.println("------------ Start -------------");
        System.out.println("Parameter class: "+ p.getType());
        System.out.println("Parameter name "+p.getName());
        System.out.println("Modifiers "+ p.getModifiers());
        System.out.println("------------ End -------------");
     
    }
}
