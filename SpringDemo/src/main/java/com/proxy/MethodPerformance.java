package com.proxy;

/**
 * Created by mi on 16-9-22.
 */
public class MethodPerformance {
    private long begin;
    private long end;
    private String serviceMethod;

    public MethodPerformance(String serviceMethod) {
        this.serviceMethod = serviceMethod;
        this.begin = System.currentTimeMillis();
    }

    public void printPerformance() {
        end = System.currentTimeMillis();
        long elapse = end - begin;

        System.out.println(serviceMethod + "take " + elapse + "ms");
    }


}
