package com.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by mi on 16-9-22.
 */
public class Reflection {
    public static Car initByDefaultConst() throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, NoSuchFieldException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class clazz = classLoader.loadClass("com.reflection.Car");

        Constructor<Car> constructor = clazz.getDeclaredConstructor((Class[])null);
        Car car = constructor.newInstance();

        Method setBrand = clazz.getDeclaredMethod("setBrand", String.class);
        setBrand.invoke(car, "红旗CA72");
        Method setColor = clazz.getDeclaredMethod("setColor", String.class);
        setColor.invoke(car, "黑色");
        Method setMaxSpeed = clazz.getDeclaredMethod("setMaxSpeed", int.class);
        setMaxSpeed.invoke(car, 200);

        Field brand = clazz.getDeclaredField("brand");
        System.out.println("brand: " + brand.getName());

        return car;
    }

    public static void main(String[] args) {
        try {
            Car car = initByDefaultConst();
            car.introduce();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
