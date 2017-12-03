package state;

/**
 * Created by leo on 2017/12/3.
 */
public class StateTest {
    public static void main(String[] args) {
        Context context = new Context();
        context.setState(new Rain());
        System.out.println(context.stateMessage());
        context.setState(new Sunshine());
        System.out.println(context.stateMessage());
    }
}
