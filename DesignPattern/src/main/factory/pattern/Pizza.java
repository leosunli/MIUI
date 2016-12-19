package factory.pattern;

/**
 * Created by mi on 16-12-14.
 */
public abstract class Pizza {
    String name;
    Dough dough;
    Sauce sauce;
    Veggies veggies[];
    Cheese cheese;
    Pepperoni pepperoni;
    Clams clams;

    abstract void prepare();

    void bake() {}
    void cut() {}
    void box() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
