package decorator;

/**
 * Created by leo on 2016/12/18.
 */
public class HouseBlend extends Beverage {

    public HouseBlend() {
        description = "HouseBlend";
    }

    @Override
    protected double cost() {
        return 0.89;
    }
}
