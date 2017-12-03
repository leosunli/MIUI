package proxy.dynamic;

import java.lang.reflect.Proxy;

/**
 * Created by leo on 2017/12/3.
 */
public class DynamicProxy {
    public static void main(String[] args) {
        Subject real = new RealSubject();
        Subject proxySubject = (Subject) Proxy.newProxyInstance(
                Subject.class.getClassLoader(),
                new Class[]{Subject.class},
                new ProxyHandler(real)
        );

        proxySubject.doSomething();
    }
}
