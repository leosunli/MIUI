package factory.method.pattern;

/**
 * Created by mi on 16-12-14.
 */
public class NYPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String type) {
        Pizza pizza = null;
        if (type.equals("cheese")) {
            pizza = new NYCheesePizza();
        } else if (type.equals("clam")) {
            pizza = new NYClamPizza();
        }
        return pizza;
    }
}
