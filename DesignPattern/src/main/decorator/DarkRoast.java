package decorator;

/**
 * Created by leo on 2016/12/18.
 */
public class DarkRoast extends Beverage {

    public DarkRoast() {
        description = "DarkRoast";
    }

    @Override
    protected double cost() {
        return 0.2;
    }
}
