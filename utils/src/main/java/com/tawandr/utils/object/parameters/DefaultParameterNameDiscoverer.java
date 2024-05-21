package com.tawandr.utils.object.parameters;


import com.tawandr.utils.object.ClassUtils;

public class DefaultParameterNameDiscoverer extends ParameterNameDiscoverer {
    private static final boolean kotlinPresent = ClassUtils.isPresent("kotlin.Unit", DefaultParameterNameDiscoverer.class.getClassLoader());

    public DefaultParameterNameDiscoverer() {
        this.addDiscoverer(new StandardReflectionParameterNameDiscoverer());
    }
}
