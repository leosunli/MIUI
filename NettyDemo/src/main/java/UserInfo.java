import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by leo on 16-8-24.
 */
public class UserInfo implements Serializable {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public byte[] codeC() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.getUsername().getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}
