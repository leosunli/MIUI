import com.spring.FuckSpring;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by mi on 16-9-22.
 */
public class Test {
    public static void main(String[] args) {
//        ApplicationContext ctx = new FileSystemXmlApplicationContext(
//                "SpringDemo/src/main/resources/applicationContext.xml");

        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        FuckSpring fuckSpring = (FuckSpring) ctx.getBean("myBean");
        fuckSpring.show();
    }
}
