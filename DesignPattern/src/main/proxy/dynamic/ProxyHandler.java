package proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by leo on 2017/12/3.
 */
public class ProxyHandler implements InvocationHandler {

    private Object proxy;

    public ProxyHandler(Object proxy) {
        this.proxy = proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在调用具体目标对象之前，可以执行一些功能处理

        //调用具体目标对象的方法
        return method.invoke(this.proxy, args);
        //调用目标对象之后，可以执行一些功能处理
    }
}
