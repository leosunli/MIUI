package decorator;

/**
 * Created by leo on 2016/12/18.
 */
public abstract class Beverage {
    String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    protected abstract double cost();
}
