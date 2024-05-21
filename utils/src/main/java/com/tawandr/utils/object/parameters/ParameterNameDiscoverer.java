package com.tawandr.utils.object.parameters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ParameterNameDiscoverer {
    private final List<ParameterNameDiscoverer> parameterNameDiscoverers = new LinkedList();
    private static final Logger log = LoggerFactory.getLogger(ParameterNameDiscoverer.class);

    public ParameterNameDiscoverer() {
    }

    public void addDiscoverer(ParameterNameDiscoverer pnd) {
        this.parameterNameDiscoverers.add(pnd);
    }

    public String[] getParameterNames(Method method) {
        log.info("getting parameters for:\n{}", method.getName());
        Iterator var2 = this.parameterNameDiscoverers.iterator();

        String[] result;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            ParameterNameDiscoverer pnd = (ParameterNameDiscoverer)var2.next();
            result = pnd.getParameterNames(method);
        } while(result == null);

        return result;
    }

    public String[] getParameterNames(Constructor<?> ctor) {
        Iterator var2 = this.parameterNameDiscoverers.iterator();

        String[] result;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            ParameterNameDiscoverer pnd = (ParameterNameDiscoverer)var2.next();
            result = pnd.getParameterNames(ctor);
        } while(result == null);

        return result;
    }
}
