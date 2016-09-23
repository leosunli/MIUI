package com.reflection;

/**
 * Created by mi on 16-9-22.
 */
public class ClassLoadTest {
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("current loader: " + classLoader);
        System.out.println("parent loader: " + classLoader.getParent());
        System.out.println("grandparent loader: " + classLoader.getParent().getParent());
    }
}
