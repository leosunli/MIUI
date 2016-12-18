package decorator;

/**
 * Created by leo on 2016/12/18.
 */
public class Espresso extends Beverage {

    public Espresso() {
        description = "Espresso";
    }

    @Override
    protected double cost() {
        return 1.99;
    }
}
