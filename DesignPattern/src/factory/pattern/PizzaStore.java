package factory.pattern;


/**
 * 工厂方法模式:定义了一个创建对象的接口,但由子类决定要实例化的类是哪一个.
 * 工厂方法让类把实例化推迟到子类.
 */
public abstract class PizzaStore {
    public Pizza orderPizza(String type) {
        Pizza pizza;

        pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    protected abstract Pizza createPizza(String type);
}
