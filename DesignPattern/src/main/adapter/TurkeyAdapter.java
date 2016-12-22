package adapter;

/**
 * 适配器模式：将一个类的接口,转换成客户期望的另一个接口。
 * 适配器让原本接口不兼容的类可以合作无间
 */
public class TurkeyAdapter implements Duck {

    Turkey turkey;

    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }

    @Override
    public void fly() {
        for (int i = 0; i < 5; i++) {
            turkey.fly();
        }
    }
}
