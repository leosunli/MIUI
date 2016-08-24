import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by leo on 16-8-24.
 */
public class TestUserInfo {

    public static void main(String[] args) throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("Welcome to Netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(userInfo);
        os.flush();
        os.close();

        byte[] b = bos.toByteArray();

        System.out.println("The jdk serializable length is :" + b.length);
        bos.close();
        System.out.println("The byte array serializable length is :" + userInfo.codeC().length);
    }
}
