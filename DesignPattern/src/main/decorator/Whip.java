package decorator;

/**
 * Created by leo on 2016/12/18.
 */
public class Whip extends CondimentDecorator {
    Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whip";
    }

    @Override
    protected double cost() {
        return 0.1 + beverage.cost();
    }
}
