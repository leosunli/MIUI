package factory.pattern;

/**
 * 抽象工厂模式: 提供一个接口,用于创建相关或依赖对象的家族,
 * 而不需要明确指定具体类
 */
public interface PizzaIngredientFactory {

    Dough createDough();
    Sauce createSauce();
    Cheese createCheese();
    Veggies createVeggies();
    Pepperoni createPepperoni();
    Clams createClams();
}
