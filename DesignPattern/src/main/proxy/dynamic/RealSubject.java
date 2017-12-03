package proxy.dynamic;

/**
 * Created by leo on 2017/12/3.
 */
public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println("call something");
    }
}
