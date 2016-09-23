package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by mi on 16-9-22.
 */
public class PerfromanceHandler implements InvocationHandler {
    private Object target;

    public PerfromanceHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        PerfromanceMonitor.begin(target.getClass().getName() + "." + method.getName());
        Object obj = method.invoke(target, args);
        System.out.println("obj:" + obj);
        PerfromanceMonitor.end();
        return obj;
    }
}
