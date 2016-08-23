import java.nio.ByteBuffer;

/**
 * Created by mi on 16-8-23.
 */
public class Test {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byte[] bytes = new byte[]{1, 2, 3, 4};
        System.out.println("byteBuffer.remaining()" + byteBuffer.remaining());
        byteBuffer.get(bytes);
        System.out.println("byteBuffer.remaining()" + byteBuffer.remaining());
        System.out.println("byteBuffer.hasRemaining()" + byteBuffer.hasRemaining());
        byteBuffer.position(7);
        byteBuffer.limit(7);
        System.out.println("byteBuffer.hasRemaining()" + byteBuffer.hasRemaining());
        System.out.println("byteBuffer.remaining()" + byteBuffer.remaining());

    }

}
